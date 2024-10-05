package ru.lewis.core.command.item

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "more", aliases = ["stack"])
@Permission("core.command.more")
class MoreCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val messages get() = configurationService.messages.common.more

    @Execute
    fun execute(@Context sender: User) {

        val inventory = sender.getBase().inventory
        val itemInMainHand = inventory.itemInMainHand

        itemInMainHand.amount = 64

        sender.getBase().sendMessage(messages.info.feedBack)

    }

    @Execute
    @Permission("core.command.more.others")
    fun execute(@Context sender: User,
                @Arg target: User
    ) {

        val inventory = target.getBase().inventory
        val itemInMainHand = inventory.itemInMainHand

        itemInMainHand.amount = 64

        sender.getBase().sendMessage(messages.info.feedBackToTarget.resolve(
            Placeholder.unparsed(
                "player", target.getName()
            )
        ))
        target.getBase().sendMessage(messages.info.feedBackTarget.resolve(
            Placeholder.unparsed(
                "player", sender.getName()
            )
        ))

    }

}