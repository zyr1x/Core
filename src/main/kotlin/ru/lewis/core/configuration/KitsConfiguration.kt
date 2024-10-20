package ru.lewis.core.configuration

import org.bukkit.Material
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.lewis.core.configuration.type.ItemTemplate
import ru.lewis.core.configuration.type.KitTemplate
import java.time.Duration

@ConfigSerializable
data class KitsConfiguration(

    val kits: MutableList<KitTemplate> = mutableListOf(
        KitTemplate(
            "golden_apple",
            Duration.ofHours(1),
            mapOf(
                Pair(
                    "1",
                    ItemTemplate(
                        Material.GOLDEN_APPLE,
                    )
                )
            )
        )
    )

)