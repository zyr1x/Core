package ru.lewis.core.command.features

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import ru.boomearo.langhelper.versions.TranslateManager
import ru.lewis.core.extension.adventure
import ru.lewis.core.extension.format
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import java.time.Duration

@Command(name = "effect")
@Permission("core.command.effect")
class EffectCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val translateManager: TranslateManager
) {

    private val config get() = configurationService.config
    private val messages get() = configurationService.messages.common.effect

    @Execute
    fun execute(@Context sender: User,
                @Arg potionEffectType: PotionEffectType,
                @Arg duration: Duration,
                @Arg amplifier: Int) {

        sender.getBase().addPotionEffect(PotionEffect(
            potionEffectType,
            (duration.toMillis() / 50).toInt(),
            amplifier)
        )

        sender.getBase().adventure.sendMessage(messages.info.feedBack.resolve(
            Placeholder.unparsed(
                "effect", translateManager.getPotionEffectName(potionEffectType, config.language)
            ),
            Placeholder.unparsed(
                "duration", duration.format()
            ),
            Placeholder.unparsed(
                "amplifier", amplifier.toString()
            )
        ))
    }

    @Permission("core.command.effect.others")
    @Execute
    fun execute(@Context sender: User,
                @Arg target: User,
                @Arg potionEffectType: PotionEffectType,
                @Arg duration: Duration,
                @Arg amplifier: Int) {

        target.getBase().addPotionEffect(PotionEffect(
            potionEffectType,
            (duration.toMillis() / 50).toInt(),
            amplifier)
        )

        sender.getBase().adventure.sendMessage(messages.info.feedBackToTarget.resolve(
            Placeholder.unparsed(
                "player", target.getName()
            ),
            Placeholder.unparsed(
                "effect", translateManager.getPotionEffectName(potionEffectType, config.language)
            ),
            Placeholder.unparsed(
                "duration", duration.format()
            ),
            Placeholder.unparsed(
                "amplifier", amplifier.toString()
            )
        ))

        target.getBase().adventure.sendMessage(messages.info.feedBackTarget.resolve(
            Placeholder.unparsed(
                "player", sender.getName()
            ),
            Placeholder.unparsed(
                "effect", translateManager.getPotionEffectName(potionEffectType, config.language)
            ),
            Placeholder.unparsed(
                "duration", duration.format()
            ),
            Placeholder.unparsed(
                "amplifier", amplifier.toString()
            )
        ))

    }

}