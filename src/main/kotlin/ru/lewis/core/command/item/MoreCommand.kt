package ru.lewis.core.command.item

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
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

}