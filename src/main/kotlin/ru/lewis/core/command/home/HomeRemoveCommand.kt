package ru.lewis.core.command.home

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "removehome", aliases = ["delhome"])
class HomeRemoveCommand @Inject constructor(
    private val configurationService: ConfigurationService
){

    private val messages get() = configurationService.messages.common.homeRemove

    @Execute
    fun removeHome(
        @Context user: User,
        @Arg name: String
    ) {

        if (user.delHome(name)) {

            user.getBase().sendMessage(
                messages.info.feedBack.resolve(
                    Placeholder.unparsed(
                        "name",
                        name
                    )
                )
            )

        } else {

            user.getBase().sendMessage(
                messages.error.homeNotFound
            )

        }
    }

}