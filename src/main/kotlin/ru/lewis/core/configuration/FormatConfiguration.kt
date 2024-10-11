package ru.lewis.core.configuration

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.lewis.core.configuration.type.MiniMessageComponent
import ru.lewis.core.extension.asMiniMessageComponent

@ConfigSerializable
class FormatConfiguration(

    val nearby: Nearby = Nearby()

) {

    @ConfigSerializable
    data class Nearby(

        val format: MiniMessageComponent = "<player> <direction> <distance>,".asMiniMessageComponent(),
        val direction: Direction = Direction()

    ) {

        @ConfigSerializable
        data class Direction(

            val south: MiniMessageComponent = "south".asMiniMessageComponent(),
            val north: MiniMessageComponent = "north".asMiniMessageComponent(),
            val east: MiniMessageComponent = "east".asMiniMessageComponent(),
            val west: MiniMessageComponent = "west".asMiniMessageComponent(),

            val southeast: MiniMessageComponent = "southeast".asMiniMessageComponent(),
            val southwest: MiniMessageComponent = "southwest".asMiniMessageComponent(),

            val northeast: MiniMessageComponent = "northeast".asMiniMessageComponent(),
            val northwest: MiniMessageComponent = "northwest".asMiniMessageComponent()

        )

    }

}