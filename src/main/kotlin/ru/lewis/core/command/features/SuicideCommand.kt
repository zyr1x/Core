package ru.lewis.core.command.features

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "suicide")
@Permission("core.command.suicide")
class SuicideCommand @Inject constructor(

    private val configurationService: ConfigurationService

) {

    private val messages get() = configurationService.messages.common.suicide

    @Execute
    fun execute(@Context sender: User) {

        sender.getBase().health = 0.0
        sender.getBase().sendMessage(messages.info.feedBack)

    }

}