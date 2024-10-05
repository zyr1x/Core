package ru.lewis.core.command.argument

import dev.rollczi.litecommands.argument.Argument
import dev.rollczi.litecommands.argument.parser.ParseResult
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.suggestion.SuggestionContext
import dev.rollczi.litecommands.suggestion.SuggestionResult
import jakarta.inject.Inject
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.potion.PotionEffectType
import ru.boomearo.langhelper.LangHelper
import ru.boomearo.langhelper.versions.LangType
import ru.boomearo.langhelper.versions.TranslateManager
import ru.lewis.core.service.ConfigurationService

class  EnchantmentArgument @Inject constructor(

    private val configurationService: ConfigurationService,
    private val translateManager: TranslateManager

): ArgumentResolver<CommandSender, Enchantment>() {

    private val config get() = configurationService.config
    private val messages get() = configurationService.messages.common.enchants

    private val arguments: MutableMap<String, Enchantment> by lazy {
        mutableMapOf<String, Enchantment>().apply {

            Enchantment.values().forEach { value ->

                put(
                    translateManager.getEnchantmentName(
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

    override fun parse(invocation: Invocation<CommandSender>, context: Argument<Enchantment>, argument: String): ParseResult<Enchantment> {
        val enchantment = arguments[argument.lowercase()]
        return if (enchantment == null) {
            ParseResult.failure(messages.help.info)
        } else {
            ParseResult.success(enchantment)
        }
    }

    override fun suggest(invocation: Invocation<CommandSender>, argument: Argument<Enchantment>, context: SuggestionContext): SuggestionResult {
        return SuggestionResult.of(arguments.keys)
    }
}