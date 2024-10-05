package ru.lewis.core.command.time

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "day")
@Permission("core.command.time.day")
class DayCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val server: Server,
    private val plugin: Plugin
) {

    private val messages get() = configurationService.messages.common.time

    @Execute
    fun execute(@Context sender: User) {

        val world = sender.getBase().location.world

        server.globalRegionScheduler.execute(plugin) {
            world.time = 1000
        }

        sender.getBase().sendMessage(messages.info.day.resolve(
            Placeholder.unparsed(
                "world", world.name
            )
        ))
    }
}
