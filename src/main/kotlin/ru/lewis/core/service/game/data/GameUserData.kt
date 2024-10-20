package ru.lewis.core.service.game.data

import com.google.inject.assistedinject.Assisted
import jakarta.inject.Inject
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.hibernate.SessionFactory
import ru.lewis.core.configuration.type.KitTemplate
import ru.lewis.core.extension.runAsync
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.hibernate.entity.PlayerDataHomeHibernateEntity
import ru.lewis.core.model.hibernate.entity.PlayerKitCooldownHibernateEntity
import ru.lewis.core.model.templates.Home
import ru.lewis.core.service.ConfigurationService

class GameUserData @Inject constructor(
    private val configurationService: ConfigurationService,
    private val assistedInjectFactories: AssistedInjectFactories,
    @Assisted private val sessionFactory: SessionFactory
) {

    inner class PlayerDataHomeActual(
        private val offlinePlayer: OfflinePlayer,
    ) {

        private val data: MutableList<Home> = mutableListOf()

        init {
            this.loadData()
        }

        private fun loadData() {

            runAsync {

                sessionFactory.inTransaction { session ->

                    val criteriaBuilder = session.criteriaBuilder
                    val criteriaQuery = criteriaBuilder.createQuery(PlayerDataHomeHibernateEntity::class.java)
                    val root = criteriaQuery.from(PlayerDataHomeHibernateEntity::class.java)

                    criteriaQuery.select(root)
                        .where(criteriaBuilder.equal(root.get<String>("owner"), this.offlinePlayer.uniqueId))

                    val homes = session.createQuery(criteriaQuery).resultList

                    homes.forEach {

                        assistedInjectFactories.createHome(
                            it.home!!,
                            Location(
                                Bukkit.getWorld(
                                    it.world!!
                                ),
                                it.xCord!!,
                                it.yCord!!,
                                it.zCord!!,
                                it.yaw!!,
                                it.pitch!!
                            )
                        )
                    }
                }
            }
        }

        fun getHome(name: String): Home? {
            return data.firstOrNull {it.getName() == name}
        }

        fun remove(name: String): Boolean {

            val home = data.firstOrNull {it.getName() == name}

            if (home == null) {
                return false
            }

            sessionFactory.inTransaction { session ->

                val criteriaBuilder = session.criteriaBuilder
                val criteriaQuery = criteriaBuilder.createQuery(PlayerDataHomeHibernateEntity::class.java)
                val root = criteriaQuery.from(PlayerDataHomeHibernateEntity::class.java)

                criteriaQuery.select(root)
                    .where(
                        criteriaBuilder.equal(root.get<String>("owner"), this.offlinePlayer.uniqueId),
                        criteriaBuilder.equal(root.get<String>("home"), name)
                    )

                val entity = session.createQuery(criteriaQuery).uniqueResult()

                session.remove(entity)
            }

            data.remove(home)

            return true
        }

        fun save(name: String): Boolean {

            var home = data.firstOrNull {it.getName() == name}

            if (home != null) {
                return false
            }

            val location = this.offlinePlayer.player?.location ?: return false

            sessionFactory.inTransaction { session ->

                val entity = PlayerDataHomeHibernateEntity(
                    this.offlinePlayer.uniqueId,
                    name,
                    location.x,
                    location.y,
                    location.z,
                    location.yaw,
                    location.pitch,
                    location.world.name
                )

                session.persist(entity)
            }

            home = assistedInjectFactories.createHome(
                name,
                location
            )

            data.add(home)

            return true
        }

        fun getData(): MutableList<Home> = data

    }

    inner class PlayerKitDataCooldownActual(
        private val offlinePlayer: OfflinePlayer,
    ) {

        private val cfg get() = configurationService.kitsConfiguration
        private val data: MutableMap<String, Long> = mutableMapOf()

        init {
            this.loadData()
        }

        private fun loadData() {

            sessionFactory.inTransaction { session ->

                val criteriaBuilder = session.criteriaBuilder
                val criteriaQuery = criteriaBuilder.createQuery(PlayerKitCooldownHibernateEntity::class.java)
                val root = criteriaQuery.from(PlayerKitCooldownHibernateEntity::class.java)

                criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get<String>("player"), this.offlinePlayer.uniqueId))

                val entities = session.createQuery(criteriaQuery).resultList

                entities.forEach { entity ->

                    val kitTemplate = cfg.kits.firstOrNull { it.name == entity.kit }

                    if (kitTemplate == null) {
                        session.remove(entity)
                        return@forEach
                    }

                    data[kitTemplate.name] = entity.cooldown!!

                }

            }

        }

        fun remove(kit: KitTemplate): Boolean {

            if (!data.containsKey(kit.name)) {
                return false
            }

            sessionFactory.inTransaction { session ->

                val criteriaBuilder = session.criteriaBuilder
                val criteriaQuery = criteriaBuilder.createQuery(PlayerKitCooldownHibernateEntity::class.java)
                val root = criteriaQuery.from(PlayerKitCooldownHibernateEntity::class.java)

                criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get<String>("player"), this.offlinePlayer.uniqueId))
                    .where(criteriaBuilder.equal(root.get<String>("kit"), kit.name))

                val entity = session.createQuery(criteriaQuery).uniqueResult()

                session.remove(entity)

            }

            data.remove(kit.name)

            return true

        }

        fun exists(kit: KitTemplate): Boolean {

            if (!data.containsKey(kit.name)) {
                return false
            }

            return data[kit.name]!! <= System.currentTimeMillis() + kit.cooldown.toMillis()

        }

        fun insert(kit: KitTemplate): Boolean {

            if (data.containsKey(kit.name)) {
                return false
            }

            val currentTimeMillis = System.currentTimeMillis()

            data[kit.name] = currentTimeMillis

            sessionFactory.inTransaction { session ->

                val entity = PlayerKitCooldownHibernateEntity(
                    offlinePlayer.uniqueId,
                    kit.name,
                    currentTimeMillis
                )

                session.persist(
                    entity
                )

            }

            return true

        }

        fun getData(): MutableMap<String, Long> = data

    }

}