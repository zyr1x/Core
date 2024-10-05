package ru.lewis.core.model.user

import org.bukkit.entity.Player
import java.util.UUID

abstract class PlayerExtension(
    private val player: Player
) {

    fun getBase(): Player = player

    fun getName(): String = player.name
    fun getUUID(): UUID = player.uniqueId

}