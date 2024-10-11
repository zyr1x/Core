package ru.lewis.core.service

import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.hibernate.SessionFactory
import ru.lewis.core.extension.asyncOnly
import ru.lewis.core.extension.runAsync
import ru.lewis.core.model.hibernate.SessionFactoryBuilder
import ru.lewis.core.model.hibernate.entity.PlayerDataHomeHibernateEntity
import ru.lewis.core.model.user.User
import kotlin.time.Duration.Companion.seconds

@Singleton
class UserDataService @Inject constructor(
    private val plugin: Plugin,
    private val configurationService: ConfigurationService,
) : TerminableModule {

    private lateinit var sessionFactory: SessionFactory
    private val config get() = configurationService.config

    override fun setup(consumer: TerminableConsumer) {

        sessionFactory = SessionFactoryBuilder.build {
            classLoader = plugin::class.java.classLoader

            val cfg = config.database

            user = cfg.user
            password = cfg.password
            driver = org.mariadb.jdbc.Driver::class
            url = "jdbc:mariadb://${cfg.address}:${cfg.port}/${cfg.database}${parametersToString(cfg.parameters)}"

            hikariProperties["maximumPoolSize"] = Runtime.getRuntime().availableProcessors().toString()
            hikariProperties["connectionTimeout"] = 10.seconds.inWholeMilliseconds.toString()
            hikariProperties["poolName"] = plugin.name

            register<PlayerDataHomeHibernateEntity>()

        }
        consumer.bind(sessionFactory)

    }

    private fun parametersToString(parameters: List<String>): String {
        return parameters.joinToString(prefix = "?", separator = "&")
    }


    fun reset(user: User) {

    }

    inner class PlayerDataHomeActual(
        val player: Player
    ) {

        private val data: MutableMap<String, Location> = mutableMapOf()

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
                        .where(criteriaBuilder.equal(root.get<String>("owner"), this.player.uniqueId.toString()))

                    val homes = session.createQuery(criteriaQuery).resultList

                    homes.forEach {

                        data[it.home!!] = Location(
                            Bukkit.getWorld(
                                it.world!!
                            ),
                            it.xCord!!,
                            it.yCord!!,
                            it.zCord!!,
                            it.yaw!!,
                            it.pitch!!
                        )

                    }
                }
            }
        }

        fun remove(name: String): Boolean {
            if (!data.containsKey(name)) {
                return false
            }

            sessionFactory.inTransaction { session ->

                val criteriaBuilder = session.criteriaBuilder
                val criteriaQuery = criteriaBuilder.createQuery(PlayerDataHomeHibernateEntity::class.java)
                val root = criteriaQuery.from(PlayerDataHomeHibernateEntity::class.java)

                criteriaQuery.select(root)
                    .where(
                        criteriaBuilder.equal(root.get<String>("owner"), this.player.uniqueId),
                        criteriaBuilder.equal(root.get<String>("home"), name)
                    )

                val home = session.createQuery(criteriaQuery).uniqueResult()

                session.remove(home)
            }

            data.remove(name)

            return true
        }

        fun save(name: String): Boolean {
            if (data.containsKey(name)) {
                return false
            }

            val location = this.player.location

            sessionFactory.inTransaction { session ->

                val entity = PlayerDataHomeHibernateEntity(
                    this.player.uniqueId,
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

            data[name] = location

            return true
        }

        fun getData(): MutableMap<String, Location> = data

    }

}