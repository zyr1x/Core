package ru.lewis.core.command.kit

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.core.configuration.type.KitTemplate
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "kitremove")
@Permission("core.command.kitremove")
class KitRemoveCommand @Inject constructor(
    private val configurationService: ConfigurationService
){

    private val kitsConfiguration get() = configurationService.kitsConfiguration
    private val messages get() = configurationService.messages.common.kitRemove

    @Execute
    fun removeKit(
        @Context sender: User,
        @Arg kit: KitTemplate
    ) {

        if (!kitsConfiguration.kits.contains(kit)) {
            sender.getBase().sendMessage(
                messages.error.notFound.resolve(
                    Placeholder.unparsed(
                        "kit",
                        kit.name
                    )
                )
            )
            return
        }

        sender.getBase().sendMessage(
            messages.info.feedBack.resolve(
                Placeholder.unparsed(
                    "kit",
                    kit.name
                )
            )
        )

        kitsConfiguration.kits.remove(kit)

        configurationService.saveKits()

    }
}