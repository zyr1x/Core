package ru.lewis.core.command.features

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.Damageable
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "fix", aliases = ["repair"])
@Permission("core.command.fix")
class FixCommand @Inject constructor(
    private val configurationService: ConfigurationService
){

    private val message get() = configurationService.messages.common.fix

    @Execute
    fun execute(@Context sender: User) {

        val inventory = sender.getBase().inventory
        val item = inventory.itemInMainHand

        item.apply {
            itemMeta = (itemMeta as Damageable).apply {
                damage = 0
            }
        }

        sender.getBase().sendMessage(
            message.info.feedBack.resolve(
                Placeholder.unparsed(
                    "amount",
                    1.toString()
                )
            )
        )

    }

    @Execute(name = "all")
    @Permission("core.command.fix.all")
    fun executeAll(@Context sender: User) {

        val inventory = sender.getBase().inventory
        var amount = 0

        for (item in inventory.contents) {

            item?.apply {
                itemMeta = (itemMeta as Damageable).apply {
                    amount++
                    damage = 0
                }
            } ?: continue

        }

        sender.getBase().sendMessage(
            message.info.feedBack.resolve(
                Placeholder.unparsed(
                    "amount",
                    amount.toString()
                )
            )
        )

    }

}