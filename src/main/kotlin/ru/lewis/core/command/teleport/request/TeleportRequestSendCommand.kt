package ru.lewis.core.command.teleport.request

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import me.lucko.helper.Schedulers
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import java.util.concurrent.TimeUnit

@Command(name = "tpa", aliases = ["tpr"])
@Permission("core.command.tpa")
class TeleportRequestSendCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val assistedInjectFactories: AssistedInjectFactories
) {

    private val messages get() = configurationService.messages.common
    private val teleportPeriod get() = configurationService.config.performance.teleportPeriod

    @Execute
    fun sendRequest(
        @Context sender: User,
        @Arg target: User
    ) {

        if (target.teleportRequests().contains(sender)) {
            sender.getBase().sendMessage(
                messages.teleportRequest.error.repeatRequest.resolve(
                    Placeholder.unparsed(
                        "player",
                        target.getName()
                    )
                )
            )
            return
        }

        target.sendRequest(sender)

        sender.getBase().sendMessage(
            messages.teleportRequest.info.feedBack.resolve(
                Placeholder.unparsed(
                    "player",
                    target.getName()
                )
            )
        )

        target.getBase().sendMessage(
            messages.teleportRequest.info.feedBackTarget.resolve(
                Placeholder.unparsed(
                    "player",
                    sender.getName()
                )
            )
        )

        Schedulers.builder().async().after(
            teleportPeriod.toSeconds(),
            TimeUnit.SECONDS
        ).run {
            target.teleportRequests().remove(sender)
        }

    }

}