package ru.lewis.core.model.user

import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import ru.lewis.core.model.templates.Home
import ru.lewis.core.model.templates.Warp
import ru.lewis.core.service.UserDataService
import ru.lewis.core.service.WarpDataService

abstract class UserData(

    private val offlinePlayer: OfflinePlayer,
    private val playerDataHomeActual: UserDataService.PlayerDataHomeActual,
    private val warpDataService: WarpDataService

) : PlayerExtension(offlinePlayer) {

    private lateinit var lastLocation: Location
    private var afk: Boolean = false

    fun getHome(name: String): Home? {
        return playerDataHomeActual.getHome(name)
    }

    fun getWarp(name: String): Warp? {
        return warpDataService.getOwnerWarp(name, this.getUUID())
    }

    fun getWarps(): List<Warp> {
        return warpDataService.getWarps(this.getUUID())
    }

    fun setWarp(name: String): Boolean {
        return warpDataService.save(
            name,
            this.getUUID(),
            this.getBase().location,
        )
    }

    fun delWarp(name: String): Boolean {
        return warpDataService.remove(
            name
        )
    }

    fun getHomes(): List<Home> {
        return playerDataHomeActual.getData()
    }

    fun setHome(name: String): Boolean {
        return playerDataHomeActual.save(name)
    }

    fun delHome(name: String): Boolean {
        return playerDataHomeActual.remove(name)
    }

    fun hasHome(): Boolean {
        return playerDataHomeActual.getData().isNotEmpty()
    }

    fun getLastLocation(): Location = lastLocation

    fun setLastLocation(location: Location) {
        this.lastLocation = location
    }

    fun isAfk(): Boolean = afk

    fun setAfk(value: Boolean) {
        this.afk = value
    }


}