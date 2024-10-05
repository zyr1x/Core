package ru.lewis.core.command.inventory

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Permission("core.command.clear")
@Command(name = "clear")
class ClearInventoryCommand @Inject constructor(
    private val configurationService: ConfigurationService
)  {

    private val message get() = configurationService.messages.common.clearInventory

    @Execute
    fun execute(@Context sender: User) {

        sender.getBase().inventory.clear()
        sender.getBase().sendMessage(message.info.feedBack)
    }

    @Permission("core.command.clear.others")
    @Execute
    fun execute(@Context sender: CommandSender,
                @Arg target: User
    ) {

        target.getBase().inventory.clear()

        sender.sendMessage(message.info.feedBackToTarget.resolve(
            Placeholder.unparsed(
                "player", target.getBase().name
            )
        ))
        target.getBase().sendMessage(message.info.feedBackTarget.resolve(
            Placeholder.unparsed(
                "player", sender.name
            )
        ))
    }
}