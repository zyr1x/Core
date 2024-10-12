package ru.lewis.core.service

import com.google.inject.assistedinject.Assisted
import jakarta.inject.Inject
import org.bukkit.Bukkit
import org.bukkit.Location
import org.hibernate.SessionFactory
import ru.lewis.core.extension.runAsync
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.hibernate.entity.PlayerDataHomeHibernateEntity
import ru.lewis.core.model.hibernate.entity.WarpDataHibernateEntity
import ru.lewis.core.model.templates.Warp
import java.util.UUID

class WarpDataService @Inject constructor(
    private val assistedInjectFactories: AssistedInjectFactories,
    @Assisted private val sessionFactory: SessionFactory
) {

    init {
        this.loadData()
    }

    private val data: MutableList<Warp> = mutableListOf()

    private fun loadData() {

        runAsync {

            sessionFactory.inTransaction { session ->

                val criteriaBuilder = session.criteriaBuilder
                val criteriaQuery = criteriaBuilder.createQuery(WarpDataHibernateEntity::class.java)
                val root = criteriaQuery.from(WarpDataHibernateEntity::class.java)

                criteriaQuery.select(root)

                val warps = session.createQuery(criteriaQuery).resultList

                warps.forEach {
                    val warp = assistedInjectFactories.createWarp(
                        it.warp!!,
                        it.owner!!,
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
                    data.add(warp)
                }


            }

        }

    }

    fun remove(name: String): Boolean {

        if (!has(name)) {
            return false
        }

        this.data.firstOrNull { it.getName() == name }.let { warp ->

            sessionFactory.inTransaction { session ->
                session.find(WarpDataHibernateEntity::class.java, warp?.getName()).let {
                    session.remove(it)
                }
            }

            data.remove(warp)
        }

        return true

    }

    fun has(name: String): Boolean {
        return data.firstOrNull {it.getName() == name} != null
    }

    fun getWarps(uuid: UUID): List<Warp> {
        return data.filter { it.getOwner() == uuid }
    }

    fun save(name: String, owner: UUID, location: Location): Boolean {

        if (has(name)) {
            return false
        }

        val warp = assistedInjectFactories.createWarp(name, owner, location)

        warp.let {

            sessionFactory.inTransaction { session ->

                val entity = WarpDataHibernateEntity(
                    it.getOwner(),
                    it.getName(),
                    it.getLocation().x,
                    it.getLocation().y,
                    it.getLocation().z,
                    it.getLocation().yaw,
                    it.getLocation().pitch,
                    it.getLocation().world.name,
                )

                session.persist(entity)

            }

        }

        data.add(warp)

        return true

    }

    fun getOwnerWarp(name: String, uuid: UUID): Warp? {
        return data.firstOrNull { it.getName() == name && it.getOwner() == uuid }
    }

    fun getWarp(name: String): Warp? {
        return data.firstOrNull { it.getName() == name }
    }

    fun getData(): MutableList<Warp> = data

}