package ru.lewis.core.command.teleport

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "tp", aliases = ["teleport", "tpo"])
@Permission("core.command.teleport.player")
class TeleportCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val messages get() = configurationService.messages.common.teleport

    @Execute
    fun execute(@Context sender: User,
                @Arg target: User
                ) {

        sender.getBase().teleportAsync(target.getBase().location)

        sender.getBase().sendMessage(messages.info.teleportToPlayer.resolve(
            Placeholder.unparsed(
                "player", target.getName()
            ),
        ))
    }

}