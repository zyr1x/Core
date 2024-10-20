package ru.lewis.core.configuration.type

import org.bukkit.Material
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.time.Duration

@ConfigSerializable
data class KitTemplate(
    val name: String,
    val cooldown: Duration,
    val items: Map<String, ItemTemplate>,
    val permission: String? = null,
    val availableItem: ItemTemplate = ItemTemplate(
        Material.GOLDEN_APPLE
    ),
)