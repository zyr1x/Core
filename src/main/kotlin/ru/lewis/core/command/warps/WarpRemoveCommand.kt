package ru.lewis.core.command.warps

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.core.model.templates.Warp
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "delwarp", aliases = ["removewarp", "deletewarp"])
@Permission("core.command.delwarp")
class WarpRemoveCommand @Inject constructor(
    private val configurationService: ConfigurationService
){

    private val messages get() = configurationService.messages.common.warpRemove

    @Execute
    fun removeWarp(
        @Context user: User,
        @Arg warp: Warp
    ) {

        if (user.getWarp(warp.getName()) == null) {
            user.getBase().sendMessage(
                messages.errors.notOwned
            )
            return
        }

        user.delWarp(warp.getName())

        user.getBase().sendMessage(
            messages.info.feedBack.resolve(
                Placeholder.unparsed(
                    "name",
                    warp.getName()
                )
            )
        )

    }

}