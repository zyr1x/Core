package ru.lewis.core.model.user

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.UUID

abstract class PlayerExtension(
    private val offlinePlayer: OfflinePlayer
) {

    fun getBase(): Player = offlinePlayer.player!!

    fun getOfflinePlayer(): OfflinePlayer = offlinePlayer

    fun getName(): String = offlinePlayer.name!!
    fun getUUID(): UUID = offlinePlayer.uniqueId

}