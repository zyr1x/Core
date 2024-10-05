package ru.lewis.core.command.item

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.join.Join
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import ru.lewis.core.extension.asMiniMessageComponent
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "itemname", aliases = ["iname"])
@Permission("core.command.itemname")
class ItemNameCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val messages get() = configurationService.messages.common.itemName

    @Execute
    fun execute(@Context sender: User,
                @Join(separator = " ") name: String) {

        sender.getBase().inventory.itemInMainHand.apply {
            itemMeta = itemMeta?.apply {
                displayName(name.asMiniMessageComponent().asComponent())
            }
        }

        sender.getBase().sendMessage(messages.info.feedBack.resolve(
            Placeholder.component(
                "newname", name.asMiniMessageComponent()
            )
        ))
    }

    @Permission("core.command.itemname.others")
    @Execute
    fun execute(@Context sender: User,
                @Arg target: User,
                @Join(separator = " ") name: String) {

        target.getBase().inventory.itemInMainHand.apply {
            itemMeta = itemMeta?.apply {
                displayName(name.asMiniMessageComponent().asComponent())
            }
        }

        target.getBase().sendMessage(messages.info.feedBackTarget.resolve(
            Placeholder.unparsed(
                "player", sender.getName()
            ),
            Placeholder.component(
                "newname", name.asMiniMessageComponent()
            )
        ))

        sender.getBase().sendMessage(messages.info.feedBackToTarget.resolve(
            Placeholder.unparsed(
                "player", target.getName()
            ),
            Placeholder.component(
                "newname", name.asMiniMessageComponent()
            )
        ))
    }
}