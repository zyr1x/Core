package ru.lewis.core.command.item

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import ru.boomearo.langhelper.versions.TranslateManager
import ru.lewis.core.extension.adventure
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "enchant")
@Permission("core.command.enchant")
class EnchantCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val translateManager: TranslateManager
) {

    private val config get() = configurationService.config
    private val messages get() = configurationService.messages.common.enchants

    @Execute
    fun execute(@Context sender: User,
                @Arg enchantment: Enchantment,
                @Arg level: Int) {

        val inventory = sender.getBase().inventory
        val itemInMainHand = inventory.itemInMainHand

        itemInMainHand.apply {
            itemMeta = itemMeta?.apply {
                addEnchant(enchantment, level, true)
            }
        }

        sender.getBase().adventure.sendMessage(messages.info.feedBack.resolve(
            Placeholder.unparsed(
                "enchantment",
                translateManager.getEnchantmentName(enchantment, config.language)
            ),
            Placeholder.unparsed(
                "level", level.toString()
            )
        ))
    }

    @Permission("core.command.enchantment.others")
    @Execute
    fun execute(@Context sender: User,
                @Arg target: User,
                @Arg enchantment: Enchantment,
                @Arg level: Int) {

        val inventory = target.getBase().inventory
        val itemInMainHand = inventory.itemInMainHand

        itemInMainHand.apply {
            itemMeta = itemMeta?.apply {
                addEnchant(enchantment, level, true)
            }
        }

        sender.getBase().adventure.sendMessage(messages.info.feedBackToTarget.resolve(
            Placeholder.unparsed(
                "enchantment",
                translateManager.getEnchantmentName(enchantment, config.language)
            ),
            Placeholder.unparsed(
                "level",
                level.toString()
            ),
            Placeholder.unparsed(
                "player",
                target.getName()
            )
        ))

        target.getBase().adventure.sendMessage(messages.info.feedBackTarget.resolve(
            Placeholder.unparsed(
                "enchantment",
                translateManager.getEnchantmentName(enchantment, config.language)
            ),
            Placeholder.unparsed(
                "level",
                level.toString()
            ),
            Placeholder.unparsed(
                "player",
                sender.getName()
            )
        ))

    }

}