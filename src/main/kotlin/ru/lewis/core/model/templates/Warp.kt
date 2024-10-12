package ru.lewis.core.model.templates

import org.bukkit.Location
import java.util.UUID

interface Warp {

    fun getName(): String
    fun getOwner(): UUID
    fun getLocation(): Location

}