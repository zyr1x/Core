package ru.lewis.core.model.user

import org.bukkit.Location
import org.bukkit.entity.Player
import ru.lewis.core.service.UserDataService

abstract class UserData(

    private val player: Player,
    private val playerDataHomeActual: UserDataService.PlayerDataHomeActual

) : PlayerExtension(player) {

    private lateinit var lastLocation: Location
    private var afk: Boolean = false

    fun getHome(name: String): Location? {
        return playerDataHomeActual.getData()[name]
    }

    fun getHomes(): List<String> {
        return playerDataHomeActual.getData().map { it.key }
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