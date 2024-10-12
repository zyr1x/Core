package ru.lewis.core.command.argument

import dev.rollczi.litecommands.argument.Argument
import dev.rollczi.litecommands.argument.parser.ParseResult
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.suggestion.SuggestionContext
import dev.rollczi.litecommands.suggestion.SuggestionResult
import jakarta.inject.Inject
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.lewis.core.model.templates.Warp
import ru.lewis.core.service.ConfigurationService
import ru.lewis.core.service.GlobalService

class WarpArgument @Inject constructor(
    private val globalService: GlobalService,
    private val configurationService: ConfigurationService
): ArgumentResolver<CommandSender, Warp>() {

    private val messages get() = configurationService.messages.common.warpTeleport
    private val errors get() = configurationService.messages.errors

    override fun parse(inv: Invocation<CommandSender>?, p1: Argument<Warp>?, arg: String): ParseResult<Warp> {

        if (inv?.sender() !is Player) {
            return ParseResult.failure(errors.onlyPlayer.asComponent())
        }

        val warp = globalService.getWarpData().getWarp(arg)
            ?: return ParseResult.failure(
                messages.errors.warpNotFound.asComponent()
            )

        return ParseResult.success(warp)

    }

    override fun suggest(
        invocation: Invocation<CommandSender>?,
        argument: Argument<Warp>?,
        context: SuggestionContext?
    ): SuggestionResult {

        if (invocation?.sender() !is Player) {
            return SuggestionResult.empty()
        }

        return SuggestionResult.of(
            globalService.getWarpData().getData()
                .map { it.getName() }
        )

    }

}