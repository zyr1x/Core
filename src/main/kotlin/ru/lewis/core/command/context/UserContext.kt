package ru.lewis.core.command.context

import dev.rollczi.litecommands.context.ContextProvider
import dev.rollczi.litecommands.context.ContextResult
import dev.rollczi.litecommands.invocation.Invocation
import jakarta.inject.Inject
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import ru.lewis.core.model.manager.UserManager

class UserContext @Inject constructor(
    private val configurationService: ConfigurationService,
    private val userService: UserManager
): ContextProvider<CommandSender, User> {

    private val errors get() = configurationService.messages.errors

    override fun provide(invocation: Invocation<CommandSender>?): ContextResult<User> {

        val sender = invocation?.sender()

        if (sender !is Player) {
            return ContextResult.error(
                errors.onlyPlayer.asComponent()
            )
        }

        val user = userService.getUser(sender)

        return ContextResult.ok { user }

    }

}