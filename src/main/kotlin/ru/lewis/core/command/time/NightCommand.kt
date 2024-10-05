package ru.lewis.core.command.time

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.lewis.core.bootstrap.Bootstrap
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "night")
@Permission("core.command.time.night")
class NightCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val server: Server
) {

    private val messages get() = configurationService.messages.common.time

    @Execute
    fun execute(@Context sender: User) {

        val world = sender.getBase().location.world
        server.globalRegionScheduler.execute(JavaPlugin.getPlugin(Bootstrap::class.java)) {
            world.time = 13000
        }

        sender.getBase().sendMessage(messages.info.night.resolve(
            Placeholder.unparsed(
                "world", world.name
            )
        ))
    }
}
