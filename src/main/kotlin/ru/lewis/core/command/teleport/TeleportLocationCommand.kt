package ru.lewis.core.command.teleport

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Location
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "tploc", aliases = ["tppos"])
@Permission("core.command.teleport.location")
class TeleportLocationCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val messages get() = configurationService.messages.common.teleport

    @Execute
    fun execute(
        @Context sender: User,
        @Arg x: Double,
        @Arg y: Double,
        @Arg z: Double
    ) {

        val world = sender.getBase().world
        val location = Location(world, x, y, z)
        sender.getBase().teleportAsync(location)
        sender.getBase().sendMessage(
            messages.info.locationTeleport.resolve(
                Placeholder.unparsed(
                    "world", world.name
                ),
                Placeholder.unparsed(
                    "x", x.toString()
                ),
                Placeholder.unparsed(
                    "y", y.toString()
                ),
                Placeholder.unparsed(
                    "z", z.toString()
                ),
            )
        )
    }
}