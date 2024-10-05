package ru.lewis.core.command.spawn

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import ru.lewis.core.extension.adventure
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Permission("core.command.setspawn")
@Command(name = "setspawn")
class SetSpawnCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {
    private val settings get() = configurationService.config.spawnSettings
    private val messages get() = configurationService.messages.common.setSpawn

    @Execute
    fun execute(@Context sender: User) {

        val location = sender.getBase().location

        settings.location.x = location.x
        settings.location.y = location.y
        settings.location.z = location.z
        settings.location.yaw = location.yaw
        settings.location.pitch = location.pitch
        settings.world = location.world.name

        configurationService.saveConfig()

        sender.getBase().adventure.sendMessage(messages.info.feedBack.resolve(
            Placeholder.unparsed(
                "world", location.world.name
            ),
            Placeholder.unparsed(
                "x", location.x.toString()
            ),
            Placeholder.unparsed(
                "y", location.x.toString()
            ),
            Placeholder.unparsed(
                "z", location.x.toString()
            ),
        ))

    }

}