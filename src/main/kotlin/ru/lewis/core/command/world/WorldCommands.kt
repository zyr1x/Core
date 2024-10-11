package ru.lewis.core.command.world

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.command.RootCommand
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Server
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import ru.lewis.core.bootstrap.Bootstrap
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@RootCommand
class WorldCommands @Inject constructor(
    private val configurationService: ConfigurationService,
    private val server: Server,
    private val plugin: Plugin
){

    private val messages get() = configurationService.messages.common

    @Execute(name = "day")
    @Permission("core.command.time.day")
    fun setDay(@Context sender: User) {

        val world = sender.getBase().location.world

        server.globalRegionScheduler.execute(plugin) {
            world.time = 1000
        }

        sender.getBase().sendMessage(messages.time.info.day.resolve(
            Placeholder.unparsed(
                "world", world.name
            )
        ))
    }

    @Execute(name = "night")
    @Permission("core.command.time.night")
    fun setNight(@Context sender: User) {

        val world = sender.getBase().location.world
        server.globalRegionScheduler.execute(JavaPlugin.getPlugin(Bootstrap::class.java)) {
            world.time = 13000
        }

        sender.getBase().sendMessage(messages.time.info.night.resolve(
            Placeholder.unparsed(
                "world", world.name
            )
        ))
    }

    @Execute(name = "rain")
    @Permission("core.command.weather.rain")
    fun setRain(@Context sender: User) {

        val world = sender.getBase().location.world

        server.globalRegionScheduler.execute(plugin) {
            world.setStorm(true)
            world.isThundering = false
            world.weatherDuration = 36000
        }

        sender.getBase().sendMessage(messages.weather.info.rain.resolve(
            Placeholder.unparsed(
                "world", world.name
            )
        ))
    }

    @Execute(name = "storm")
    @Permission("core.command.weather.storm")
    fun setStorm(@Context sender: User) {

        val world = sender.getBase().location.world

        server.globalRegionScheduler.execute(plugin) {
            world.setStorm(true)
            world.isThundering = true
            world.weatherDuration = 36000
        }

        sender.getBase().sendMessage(messages.weather.info.storm.resolve(
            Placeholder.unparsed(
                "world", world.name
            )
        ))
    }

    @Execute(name = "sun")
    @Permission("core.command.weather.sun")
    fun setSun(@Context sender: User) {

        val world = sender.getBase().location.world

        server.globalRegionScheduler.execute(plugin) {
            world.setStorm(false)
            world.isThundering = false
            world.weatherDuration = 36000
        }

        sender.getBase().sendMessage(messages.weather.info.sun.resolve(
            Placeholder.unparsed(
                "world", world.name
            )
        ))
    }

}