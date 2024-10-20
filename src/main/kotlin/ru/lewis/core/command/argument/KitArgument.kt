package ru.lewis.core.command.argument

import dev.rollczi.litecommands.argument.Argument
import dev.rollczi.litecommands.argument.parser.ParseResult
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.suggestion.SuggestionContext
import dev.rollczi.litecommands.suggestion.SuggestionResult
import jakarta.inject.Inject
import org.bukkit.command.CommandSender
import ru.lewis.core.configuration.type.KitTemplate
import ru.lewis.core.service.ConfigurationService

class KitArgument @Inject constructor(
    private val configurationService: ConfigurationService
): ArgumentResolver<CommandSender, KitTemplate>() {

    private val kitsConfiguration get() = configurationService.kitsConfiguration
    private val messages get() = configurationService.messages.common.kitGet

    override fun parse(
        p0: Invocation<CommandSender>?,
        p1: Argument<KitTemplate>?,
        argument: String?
    ): ParseResult<KitTemplate> {

        val kit = kitsConfiguration.kits.firstOrNull {it.name == argument}

        if (kit == null) {
            return ParseResult.failure(messages.error.notFound.asComponent())
        }

        return ParseResult.success(kit)

    }

    override fun suggest(
        invocation: Invocation<CommandSender>?,
        argument: Argument<KitTemplate>?,
        context: SuggestionContext?
    ): SuggestionResult {
        return SuggestionResult.of(
            kitsConfiguration.kits.map { it.name }
        )
    }

}