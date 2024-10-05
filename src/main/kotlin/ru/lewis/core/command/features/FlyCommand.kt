package ru.lewis.core.command.features

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "fly")
@Permission("core.command.fly")
class FlyCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val messages get() = configurationService.messages.common.fly
    private val placeholders get() = configurationService.messages.placeholders

    @Execute
    fun execute(@Context sender: User) {

        sender.getBase().allowFlight = !sender.getBase().allowFlight
        sender.getBase().isFlying = sender.getBase().allowFlight

        val statusPlaceholder = if (sender.getBase().isFlying) placeholders.enabled else placeholders.disabled

        sender.getBase().sendMessage(messages.info.feedBack.resolve(
            Placeholder.component(
                "status", statusPlaceholder.asComponent()
            )
        ))

    }

    @Execute
    @Permission("core.command.fly.other")
    fun execute(@Context sender: User,
                @Arg target: User) {

        sender.getBase().allowFlight = !sender.getBase().allowFlight
        sender.getBase().isFlying = sender.getBase().allowFlight

        val statusPlaceholder = if (target.getBase().isFlying) placeholders.enabled else placeholders.disabled

        sender.getBase().sendMessage(messages.info.feedBackToTarget.resolve(
            Placeholder.component(
                "status", statusPlaceholder.asComponent()
            ),
            Placeholder.unparsed(
                "player", target.getBase().name
            )
        ))

        target.getBase().sendMessage(messages.info.feedBackTarget.resolve(
            Placeholder.component(
                "status", statusPlaceholder.asComponent()
            ),
            Placeholder.unparsed(
                "player", sender.getBase().name
            )
        ))
    }
















//    @Execute
//    fun execute(@Context sender: ICommonPlayer, @Arg("true/false") value: Boolean) {
//
//        val bukkitPlayer = sender.bukkitPlayer()
//
//        bukkitPlayer.allowFlight = value
//        bukkitPlayer.isFlying = value
//        bukkitPlayer.sendMessage(messages.info.feedBack)
//
//    }

//    @Execute
//    fun execute(@Context sender: ICommonPlayer, @Arg target: ICommonPlayer) {
//
//        val senderBukkitPlayer = sender.bukkitPlayer()
//        val targetBukkitPlayer = target.bukkitPlayer()
//
//        val status: Boolean = !targetBukkitPlayer.allowFlight
//        targetBukkitPlayer.allowFlight = status
//        targetBukkitPlayer.isFlying = status
//
//        senderBukkitPlayer.sendMessage(messages.info.feedBackToTarget.resolve(Placeholder.unparsed("player", target.getName())))
//        targetBukkitPlayer.sendMessage(messages.info.feedBackTarget.resolve(Placeholder.unparsed("player", sender.getName())))
//
//    }
//
//    @Execute
//    fun execute(@Context sender: ICommonPlayer, @Arg target: ICommonPlayer, @Arg("true/false") value: Boolean) {
//
//        val targetBukkitPlayer = target.bukkitPlayer()
//        val senderBukkitPlayer = sender.bukkitPlayer()
//
//        targetBukkitPlayer.allowFlight = value
//        targetBukkitPlayer.isFlying = value
//
//        senderBukkitPlayer.sendMessage(messages.info.feedBackToTarget.resolve(Placeholder.unparsed("player", target.getName())))
//        targetBukkitPlayer.sendMessage(messages.info.feedBackTarget.resolve(Placeholder.unparsed("player", sender.getName())))
//
//    }

}