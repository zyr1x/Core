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

@Command(name = "flyspeed")
@Permission("core.command.flyspeed")
class FlySpeedCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val messages get() = configurationService.messages.common.flySpeed

    @Suppress("UnstableApiUsage")
    @Execute
    fun execute(@Context sender: User,
                @Arg @Key("1..10") speed: Int) {

        val value : Float = speed.toFloat() / 10.0f
        sender.getBase().flySpeed = value

        sender.getBase().sendMessage(messages.info.feedBack.resolve(
            Placeholder.unparsed(
                "speed", speed.toString()
            )
        ))

    }

    @Permission("core.command.flyspeed.others")
    @Execute
    fun execute(@Context sender: User,
                @Arg @Key("1..10") speed: Int,
                @Arg target: User) {

        val value : Float = speed.toFloat() / 10.0f
        target.getBase().flySpeed = value

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