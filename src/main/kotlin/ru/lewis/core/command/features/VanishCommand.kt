package ru.lewis.core.command.features

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "vanish", aliases = ["v"])
@Permission("core.command.vanish")
class VanishCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val messages get() = configurationService.messages.common.vanish
    private val placeholders get() = configurationService.messages.placeholders

    @Execute
    fun execute(@Context sender: User) {

        sender.getBase().isInvisible = !sender.getBase().isInvisible

        val statusPlaceholder = if (sender.getBase().isInvisible) placeholders.enabled else placeholders.disabled

        sender.getBase().sendMessage(messages.info.feedBack.resolve(
            Placeholder.component(
                "status", statusPlaceholder.asComponent()
            ),
        ))
    }

    @Execute
    fun execute(@Context sender: User,
                @Arg target: User
    ) {

        sender.getBase().isInvisible = !sender.getBase().isInvisible

        val statusPlaceholder = if (sender.getBase().isInvisible) placeholders.enabled else placeholders.disabled

        sender.getBase().sendMessage(messages.info.feedBackToTarget.resolve(
            Placeholder.component(
                "status", statusPlaceholder.asComponent()
            ),
            Placeholder.unparsed(
                "player", target.getName()
            )
        ))

        target.getBase().sendMessage(messages.info.feedBackTarget.resolve(
            Placeholder.component(
                "status", statusPlaceholder.asComponent()
            ),
            Placeholder.unparsed(
                "player", sender.getName()
            )
        ))
    }

}