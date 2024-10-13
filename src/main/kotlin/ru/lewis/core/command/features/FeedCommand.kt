package ru.lewis.core.command.features

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "feed", aliases = ["food"])
@Permission("core.command.feed")
class FeedCommand @Inject constructor(
    private val configurationService: ConfigurationService
){

    private val messages get() = configurationService.messages.common.feed

    @Execute
    fun noArgs(
        @Context user: User,
    ) {

        user.getBase().foodLevel = 20

        user.getBase().sendMessage(
            messages.info.feedBack
        )

    }

    @Execute
    fun feedPlayer(
        @Context sender: User,
        @Arg target: User
    ) {

        target.getBase().foodLevel = 20

        sender.getBase().sendMessage(
            messages.info.feedBackToTarget.resolve(
                Placeholder.unparsed(
                    "player",
                    target.getName()
                )
            )
        )

        target.getBase().sendMessage(
            messages.info.feedBackTarget.resolve(
                Placeholder.unparsed(
                    "player",
                    sender.getName()
                )
            )
        )

    }

}