package ru.lewis.core.command.spawn

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Permission("core.command.spawn")
@Command(name = "spawn")
class SpawnCommand @Inject constructor(
    private val configurationService: ConfigurationService

) {

    private val settings get() = configurationService.config.spawnSettings
    private val messages get() = configurationService.messages.common.spawn
    private val config get() = configurationService.config.soundSettings

    @Execute
    fun execute(@Context sender: User) {

        val location = Location(Bukkit.getWorld(settings.world),
            settings.location.x,
            settings.location.y,
            settings.location.z,
            settings.location.yaw,
            settings.location.pitch
        )

        sender.getBase().teleportAsync(location).thenRun {
            config.teleport.play(sender.getBase())
        }

        sender.getBase().sendMessage(messages.info.feedBack)
    }

    @Execute
    @Permission("core.command.spawn.other")
    fun execute(@Context sender: User,
                @Arg target: User
    ) {
        val location = Location(Bukkit.getWorld(settings.world),
            settings.location.x,
            settings.location.y,
            settings.location.z,
            settings.location.yaw,
            settings.location.pitch
            )

        target.getBase().teleportAsync(location).thenRun {
            config.teleport.play(target.getBase())
        }

        sender.getBase().sendMessage(messages.info.feedBackToTarget.resolve(
            Placeholder.unparsed(
                "player", target.getName()
            )
        ))
        sender.getBase().sendMessage(messages.info.feedBackTarget.resolve(
            Placeholder.unparsed(
                "player", target.getName()
            )
        ))
    }

}