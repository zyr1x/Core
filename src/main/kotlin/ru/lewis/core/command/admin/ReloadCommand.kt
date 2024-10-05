package ru.lewis.core.command.admin

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import org.bukkit.command.CommandSender
import ru.lewis.core.extension.adventure
import ru.lewis.core.service.ConfigurationService

@Command(name = "core")
class ReloadCommand @Inject constructor(
    private val configurationService: ConfigurationService,
) {



    @Permission("core.command.reload")
    @Execute(name = "reload")
    fun execute(@Context sender: CommandSender) {

        configurationService.reload()
        sender.adventure.sendMessage("reloaded config")

    }
}
