package ru.lewis.core.command.argument

import dev.rollczi.litecommands.argument.Argument
import dev.rollczi.litecommands.argument.parser.ParseResult
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.suggestion.SuggestionContext
import dev.rollczi.litecommands.suggestion.SuggestionResult
import jakarta.inject.Inject
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.lewis.core.model.manager.UserManager
import ru.lewis.core.model.templates.Home
import ru.lewis.core.service.ConfigurationService

class HomeArgument @Inject constructor(
    private val userManager: UserManager,
    private val configurationService: ConfigurationService
): ArgumentResolver<CommandSender, Home>() {

    private val messages get() = configurationService.messages.common.home
    private val errors get() = configurationService.messages.errors

    override fun parse(inv: Invocation<CommandSender>?, p1: Argument<Home>?, arg: String): ParseResult<Home> {

        if (inv?.sender() !is Player) {
            return ParseResult.failure(errors.onlyPlayer.asComponent())
        }

        val home = userManager.getUser(inv.sender() as Player).getHome(arg)
            ?: return ParseResult.failure(
                messages.error.homeNotFound.asComponent()
            )

        return ParseResult.success(home)

    }

    override fun suggest(
        invocation: Invocation<CommandSender>?,
        argument: Argument<Home>?,
        context: SuggestionContext?
    ): SuggestionResult {

        if (invocation?.sender() !is Player) {
            return SuggestionResult.empty()
        }

        return SuggestionResult.of(
            userManager.getUser(invocation.sender() as OfflinePlayer)
                .getHomes().map { it.getName() }
        )

    }


}