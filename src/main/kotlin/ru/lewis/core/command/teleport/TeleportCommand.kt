package ru.lewis.core.command.teleport

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.command.RootCommand
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Location
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@RootCommand
class TeleportCommand @Inject constructor(
    private val configurationService: ConfigurationService
){

    private val messages get() = configurationService.messages.common
    private val sounds get() = configurationService.config.soundSettings

    @Execute(name = "tppos", aliases = ["tpposition", "teleportpos", "teleportposition"])
    @Permission("core.command.tp.pos")
    fun teleportPosition(
        @Context user: User,
        @Arg x: Double,
        @Arg y: Double,
        @Arg z: Double
    ) {

        user.getBase().sendMessage(
            messages.teleportPosition.info.feedBack
        )

        user.getBase().teleportAsync(
            Location(user.getBase().world,
                x, y, z)
        ).thenRun {
            sounds.teleport.play(user.getBase())
        }

    }

    @Execute(name = "tp", aliases = ["teleport"])
    @Permission("core.command.tp.other")
    fun teleportPlayer(
        @Context sender: User,
        @Arg target: User
    ) {

        sender.getBase().sendMessage(
            messages.teleportPlayer.info.feedBack.resolve(
                Placeholder.unparsed(
                    "player",
                    target.getName()
                )
            )
        )

        sender.getBase().teleportAsync(
            target.getBase().location
        ).thenRun {
            sounds.teleport.play(sender.getBase())
        }

    }

    @Execute(name = "s", aliases = ["tphere"])
    fun tpHerePlayer(
        @Context sender: User,
        @Arg target: User
    ) {

        target.getBase().teleportAsync(
            sender.getBase().location
        ).thenRun {
            sounds.teleport.play(target.getBase())
        }

        target.getBase().sendMessage(
            messages.tpHere.info.feedBackTarget.resolve(
                Placeholder.unparsed(
                    "player",
                    sender.getName()
                )
            )
        )

        sender.getBase().sendMessage(
            messages.tpHere.info.feedBack.resolve(
                Placeholder.unparsed(
                    "player",
                    target.getName()
                )
            )
        )

    }

}