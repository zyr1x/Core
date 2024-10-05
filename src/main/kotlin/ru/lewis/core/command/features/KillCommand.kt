package ru.lewis.core.command.features

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

@Command(name = "kill")
@Permission("core.command.kill")
class KillCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {
    private val messages get() = configurationService.messages.common.kill

    @Execute
    fun execute(@Context sender: User,
                @Arg target: User) {

        target.getBase().health = 0.0

        sender.getBase().sendMessage(messages.info.feedBack.resolve(
            Placeholder.unparsed(
                "player", target.getName()
            )
        ))

        target.getBase().sendMessage(messages.info.feedBackTarget.resolve(
            Placeholder.unparsed(
                "player", sender.getName()
            )
        ))

    }

}