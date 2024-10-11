package ru.lewis.core.command.item

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import ru.boomearo.langhelper.versions.TranslateManager
import ru.lewis.core.extension.format
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import java.time.Duration

@Command(name = "potion")
@Permission("core.command.potion")
class PotionCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val translateManager: TranslateManager
){

    private val messages get() = configurationService.messages.common.potion
    private val config get() = configurationService.config

    @Execute
    fun execute(@Context sender: User, @Arg potionEffectType: PotionEffectType, @Arg duration: Duration, @Arg amplifier: Int) {

        val inventory = sender.getBase().inventory
        val itemInMainHand = inventory.itemInMainHand

        if (itemInMainHand.type == Material.POTION ||
            itemInMainHand.type == Material.SPLASH_POTION ||
            itemInMainHand.type == Material.TIPPED_ARROW) {

            itemInMainHand.apply {
                itemMeta = (itemMeta as PotionMeta).apply {

                    addCustomEffect(
                        PotionEffect(
                            potionEffectType,
                            (duration.toSeconds() * 20).toInt(),
                            amplifier
                        ),
                        true
                    )

                }
            }

            sender.getBase().sendMessage(
                messages.info.feedBack.resolve(
                    Placeholder.unparsed(
                        "potion",
                        translateManager.getPotionEffectName(potionEffectType, config.language)
                    ),
                    Placeholder.unparsed(
                        "duration",
                        duration.format()
                    ),
                    Placeholder.unparsed(
                        "amplifier",
                        amplifier.toString()
                    )
                )
            )

        } else {
            sender.getBase().sendMessage(messages.error.notValidType)
        }

    }

    @Execute
    fun executeOther(@Context sender: User, @Arg potionEffectType: PotionEffectType, @Arg duration: Duration, @Arg amplifier: Int,  @Arg target: User) {

        val inventory = target.getBase().inventory
        val itemInMainHand = inventory.itemInMainHand

        if (itemInMainHand.type == Material.POTION ||
            itemInMainHand.type == Material.SPLASH_POTION ||
            itemInMainHand.type == Material.TIPPED_ARROW) {

            itemInMainHand.apply {
                itemMeta = (itemMeta as PotionMeta).apply {

                    addCustomEffect(
                        PotionEffect(
                            potionEffectType,
                            (duration.toSeconds() * 20).toInt(),
                            amplifier
                        ),
                        true
                    )

                }
            }

            sender.getBase().sendMessage(
                messages.info.feedBackToTarget.resolve(
                    Placeholder.unparsed(
                        "potion",
                        translateManager.getPotionEffectName(potionEffectType, config.language)
                    ),
                    Placeholder.unparsed(
                        "duration",
                        duration.format()
                    ),
                    Placeholder.unparsed(
                        "amplifier",
                        amplifier.toString()
                    ),
                    Placeholder.unparsed(
                        "player",
                        target.getName()
                    )
                )
            )

            target.getBase().sendMessage(
                messages.info.feedBackTarget.resolve(
                    Placeholder.unparsed(
                        "potion",
                        translateManager.getPotionEffectName(potionEffectType, config.language)
                    ),
                    Placeholder.unparsed(
                        "duration",
                        duration.format()
                    ),
                    Placeholder.unparsed(
                        "amplifier",
                        amplifier.toString()
                    ),
                    Placeholder.unparsed(
                        "player",
                        sender.getName()
                    )
                )
            )

        } else {

            sender.getBase().sendMessage(messages.error.notValidType)

        }

    }


}