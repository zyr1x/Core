package ru.lewis.core.command.argument

import dev.rollczi.litecommands.argument.Argument
import dev.rollczi.litecommands.argument.parser.ParseResult
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.suggestion.SuggestionContext
import dev.rollczi.litecommands.suggestion.SuggestionResult
import jakarta.inject.Inject
import org.bukkit.command.CommandSender
import org.bukkit.potion.PotionEffectType
import ru.boomearo.langhelper.versions.TranslateManager
import ru.lewis.core.service.ConfigurationService

class PotionEffectTypeArgument @Inject constructor(

    private val configurationService: ConfigurationService,
    private val translateManager: TranslateManager

): ArgumentResolver<CommandSender, PotionEffectType>() {

    private val config get() = configurationService.config
    private val messages get() = configurationService.messages.common.effect

    private val arguments: MutableMap<String, PotionEffectType> by lazy {
        mutableMapOf<String, PotionEffectType>().apply {

            PotionEffectType.values().forEach { value ->

                    put(
                        translateManager.getPotionEffectName(
                            value,
                            config.language
                        )
                            .lowercase()
                            .replace(
                                " ",
                                "_"
                            ),
                        value
                    )


            }


        }
    }

    override fun parse(invocation: Invocation<CommandSender>, context: Argument<PotionEffectType>, argument: String): ParseResult<PotionEffectType> {
        val potionEffectType = arguments[argument.lowercase()]
        return if (potionEffectType == null) {
            ParseResult.failure(messages.help.info)
        } else {
            ParseResult.success(potionEffectType)
        }
    }

    override fun suggest(invocation: Invocation<CommandSender>, argument: Argument<PotionEffectType>, context: SuggestionContext): SuggestionResult {
        return SuggestionResult.of(arguments.keys)
    }
}