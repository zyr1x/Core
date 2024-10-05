package ru.lewis.core.command.speed

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.argument.Key
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "speed")
@Permission("core.command.player.speed")
class WalkSpeedCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {
    
    private val messages get() = configurationService.messages.common.walkSpeed
    
    @Suppress("UnstableApiUsage")
    @Execute
    fun execute(@Context sender: User,
                @Arg @Key("1..10") speed: Int) {

        val value : Float = speed.toFloat() / 10.0f
        sender.getBase().walkSpeed = value

        sender.getBase().sendMessage(messages.info.feedBack.resolve(
            Placeholder.unparsed(
                "speed", speed.toString()
            )
        ))

    }

    @Suppress("UnstableApiUsage")
    @Permission("core.command.walkSpeed.others")
    @Execute
    fun execute(@Context sender: User,
                @Arg target: User,
                @Arg @Key("1..10") speed: Int) {

        val value : Float = speed.toFloat() / 10.0f
        target.getBase().walkSpeed = value

        sender.getBase().sendMessage(messages.info.feedBack.resolve(
            Placeholder.unparsed(
                "speed", speed.toString()
            ),
            Placeholder.unparsed(
                "player", target.getName()
            )
        ))

        target.getBase().sendMessage(messages.info.feedBack.resolve(
            Placeholder.unparsed(
                "speed", speed.toString()
            ),
            Placeholder.unparsed(
                "player", sender.getName()
            )
        ))
    }
}