package ru.lewis.core.command.argument

import dev.rollczi.litecommands.argument.Argument
import dev.rollczi.litecommands.argument.parser.ParseResult
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.suggestion.SuggestionContext
import dev.rollczi.litecommands.suggestion.SuggestionResult
import jakarta.inject.Inject
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import ru.lewis.core.model.manager.UserManager

class UserArgument @Inject constructor(
    private val userService: UserManager,
    private val configurationService: ConfigurationService
): ArgumentResolver<CommandSender, User>() {

    private val errors get() = configurationService.messages.errors

    override fun parse(invocation: Invocation<CommandSender>, p1: Argument<User>, argument: String): ParseResult<User> {

        val player = Bukkit.getPlayer(argument)
            ?: return ParseResult.failure(
                invocation.sender().sendMessage(
                    errors.playerNotFound.asComponent()
                )
            )

        return ParseResult.success(
            userService.getUser(
                player
            )
        )

    }

    override fun suggest(
        invocation: Invocation<CommandSender>?,
        argument: Argument<User>?,
        context: SuggestionContext?
    ): SuggestionResult {

        return SuggestionResult.of(
            Bukkit.getOnlinePlayers().map {
                userService.getUser(it).getBase().name
            }
        )

    }
}