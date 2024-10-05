package ru.lewis.core.command.teleport

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.Schedulers
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import java.util.concurrent.TimeUnit

@Singleton
@Command(name = "tpa", aliases = ["tpsend", "sendteleportrequest"])
@Permission("core.command.tprequest")
class TeleportRequestCommand @Inject constructor(
    private val configurationService: ConfigurationService
){

    private val messages get() = configurationService.messages.common.teleportRequest
    private val performance get() = configurationService.config.performance

    private val players: MutableMap<User, MutableList<User>> = mutableMapOf()

    @Execute(name = "accept")
    fun acceptExecute(@Context sender: User, @Arg target: User) {


        val request = this.acceptRequest(sender, target)

        if (!request) {
            sender.getBase().sendMessage(
                messages.teleportRequestAccept.info.feedBackError
                    .resolve(
                        Placeholder.unparsed(
                            "player",
                            target.getName()
                        )
                    )
            )
        } else {
            sender.getBase().sendMessage(
                messages.teleportRequestAccept.info.feedBack
                    .resolve(
                        Placeholder.unparsed(
                            "player",
                            target.getName()
                        )
                    )
            )
        }

    }

    @Execute(name = "deny")
    fun denyExecute(@Context sender: User, @Arg target: User) {

        val request = this.denyRequest(sender, target)

        if (!request) {
            sender.getBase().sendMessage(
                messages.teleportRequestDeny.info.feedBackError
                    .resolve(
                        Placeholder.unparsed(
                            "player",
                            target.getName()
                        )
                    )
            )
        } else {
            sender.getBase().sendMessage(
                messages.teleportRequestDeny.info.feedBack
                    .resolve(
                        Placeholder.unparsed(
                            "player",
                            target.getName()
                        )
                    )
            )
        }

    }

    @Execute
    fun sendRequestExecute(@Context sender: User, @Arg target: User) {

        val request = this.sendRequest(sender, target)

        if (!request) {

            sender.getBase().sendMessage(messages.info.feedBackError)

        } else {

            sender.getBase().sendMessage(
                messages.info.feedBack.resolve(
                    Placeholder.unparsed(
                        "player",
                        target.getName()
                    )
                )
            )

            target.getBase().sendMessage(
                messages.teleportRequestAccept.info.feedBackTarget
                    .resolve(
                        Placeholder.unparsed(
                            "player",
                            sender.getName()
                        )
                    )
            )


        }

    }

    private fun existsPlayer(sender: User, target: User): Boolean {

        return players[sender]?.contains(target) ?: false

    }

    private fun insertPlayer(sender: User, target: User) {
        if (!players.containsKey(sender)) {

            players[sender] = mutableListOf(target)

        } else {

            players[sender]!!.add(target)
        }
    }

    private fun sendRequest(sender: User, target: User): Boolean {

        if (this.existsPlayer(sender, target)) return false

        this.insertPlayer(sender, target)

        Schedulers.builder()
            .async()
            .after(
                performance.teleportPeriod
                    .toSeconds(),
                TimeUnit.SECONDS
            )
            .run {
                this.players[sender]!!.remove(sender)
            }

        return true

    }

    private fun denyRequest(sender: User, target: User): Boolean {

        if (!this.existsPlayer(sender, target)) return false

        this.players[sender]!!.remove(target)

        return true

    }

    private fun acceptRequest(sender: User, target: User): Boolean {

        if (!this.existsPlayer(sender, target)) return false

        this.players[sender]!!.remove(target)
        target.getBase().teleportAsync(sender.getBase().location)

        return true
    }

}