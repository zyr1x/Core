package ru.lewis.core.command.inventory

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Permission("core.command.enderchest")
@Command(name = "enderchest", aliases = ["ec"])
class EnderChestCommand @Inject constructor(
    private val configurationService: ConfigurationService
)  {

    private val message get() = configurationService.messages.common.anvil

    @Execute
    fun execute(@Context sender: User) {

        sender.getBase().openInventory(sender.getBase().enderChest)
    }

    @Permission("core.command.enderchest.others")
    @Execute
    fun execute(@Context sender: User,
                @Arg target: User
    ) {
        sender.getBase().openInventory(target.getBase().enderChest)
    }
}