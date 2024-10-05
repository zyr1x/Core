package ru.lewis.core.command.argument

import dev.rollczi.litecommands.argument.Argument
import dev.rollczi.litecommands.argument.parser.ParseResult
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.suggestion.SuggestionContext
import dev.rollczi.litecommands.suggestion.SuggestionResult
import jakarta.inject.Inject
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import ru.lewis.core.service.ConfigurationService

class GamemodeArgument @Inject constructor(
    private val configurationService: ConfigurationService
) : ArgumentResolver<CommandSender, GameMode>() {

    private val arguments: MutableMap<String, GameMode> = GameMode.entries.associateBy(
        { it.name.lowercase() },
        { it }
    ).toMutableMap().apply {
        GameMode.entries.forEach { gameMode ->
            this[gameMode.value.toString()] = gameMode
        }
    }

    override fun parse(invocation: Invocation<CommandSender>, context: Argument<GameMode>, argument: String): ParseResult<GameMode> {
        val gameMode = arguments[argument.lowercase()]
        return if (gameMode == null) {
            ParseResult.failure(configurationService.messages.common.gameMode.help.info)
        } else {
            ParseResult.success(gameMode)
        }
    }

    override fun suggest(invocation: Invocation<CommandSender>, argument: Argument<GameMode>, context: SuggestionContext): SuggestionResult {
        return SuggestionResult.of(arguments.keys)
    }

}