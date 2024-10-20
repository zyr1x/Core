package ru.lewis.core.model.user

import io.github.blackbaroness.durationserializer.DurationFormats
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import ru.lewis.core.configuration.type.KitTemplate
import ru.lewis.core.extension.format
import ru.lewis.core.model.templates.Home
import ru.lewis.core.model.templates.Warp
import ru.lewis.core.service.game.data.GameUserData
import ru.lewis.core.service.game.data.GameWarpData
import java.time.Duration

abstract class UserData(

    private val offlinePlayer: OfflinePlayer,
    private val playerDataHomeActual: GameUserData.PlayerDataHomeActual,
    private val playerKitDataCooldownActual: GameUserData.PlayerKitDataCooldownActual,
    private val warpDataService: GameWarpData

) : PlayerExtension(offlinePlayer) {

    private lateinit var lastLocation: Location
    private var afk: Boolean = false

    fun getKitCooldown(kit: KitTemplate): Duration {

        val startCooldown: Long = playerKitDataCooldownActual.getData()[kit.name]!! + kit.cooldown.toMillis()
        val currentTime = System.currentTimeMillis()

        val newTime = startCooldown - currentTime

        if (newTime <= 0) {
            removeKitCooldown(kit)
        }

        return Duration.ofMillis(newTime)

    }

    fun existsKitCooldown(kit: KitTemplate): Boolean {
        return playerKitDataCooldownActual.exists(kit)
    }

    fun insertKitCooldown(kit: KitTemplate) {
        playerKitDataCooldownActual.insert(kit)
    }

    fun removeKitCooldown(kit: KitTemplate) {
        playerKitDataCooldownActual.remove(kit)
    }

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