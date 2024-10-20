package ru.lewis.core.command.teleport.request

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "tpcancel", aliases = ["tpc", "tpdeny"])
@Permission("core.command.tpc")
class TeleportRequestCancelCommand @Inject constructor(
    private val configurationService: ConfigurationService,
) {

    private val messages get() = configurationService.messages.common

    @Execute
    fun cancelRequest(
        @Context sender: User,
        @Arg target: User
    ) {

        if (!target.teleportRequests().contains(sender)) {
            sender.getBase().sendMessage(
                messages.teleportRequestCancel.error.playerNotFound.resolve(
                    Placeholder.unparsed(
                        "player",
                        target.getName()
                    )
                )
            )
            return
        }

        target.teleportRequests().remove(sender)

        sender.getBase().sendMessage(
            messages.teleportRequestCancel.info.feedBack.resolve(
                Placeholder.unparsed(
                    "player",
                    target.getName()
                )
            )
        )

    }

}