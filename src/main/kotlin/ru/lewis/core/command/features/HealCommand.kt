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

@Command(name = "heal")
@Permission("core.command.heal")
class HealCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val messages get() = configurationService.messages.common.heal

    @Execute
    fun execute(@Context sender: User) {

        sender.getBase().health = sender.getBase().maxHealth
        sender.getBase().sendMessage(messages.info.feedBack)

    }

    @Permission("core.command.heal.others")
    @Execute
    fun execute(@Context sender: User,
                @Arg target: User) {

        target.getBase().health = sender.getBase().maxHealth

        target.getBase().sendMessage(messages.info.feedBackTarget.resolve(
            Placeholder.unparsed(
                "player", sender.getName()
            )
        ))
        sender.getBase().sendMessage(messages.info.feedBackToTarget.resolve(
            Placeholder.unparsed(
                "player", target.getName()
            )
        ))

    }

}