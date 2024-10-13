package ru.lewis.core.command.features

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import ru.lewis.core.configuration.type.MiniMessageComponent
import ru.lewis.core.extension.asMiniMessageComponent
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import ru.lewis.core.model.manager.UserManager
import kotlin.math.sqrt

@Command(name = "near")
@Permission("core.command.near")
class NearCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val userService: UserManager
){

    private val format get() = configurationService.format.nearby
    private val settings get() = configurationService.config.nearbySettings
    private val messages get() = configurationService.messages.common.nearby

    @Execute
    fun execute(@Context sender: User) {

        for (parameter in settings.parameters) {

            if (!sender.getBase().hasPermission(parameter.permission)) {
                continue
            }

            val users = this.getNearbyPlayers(sender, parameter.radius.toDouble())

            if (users.isEmpty()) {
                sender.getBase().sendMessage(
                    messages.error.playersNotFound
                )
                break
            }

            users.forEach {
                sender.getBase().sendMessage(
                    format.format.resolve(
                        Placeholder.unparsed(
                            "player",
                            it.getName()
                        ),
                        Placeholder.component(
                            "direction",
                            this.getDirection(
                                sender.getBase(),
                                it.getBase()
                            )
                        ),
                        Formatter.number(
                            "distance",
                            this.getDistance(
                                sender,
                                it
                            )
                        )
                    )
                )
            }


        }

    }

    private fun getNearbyPlayers(player: User, radius: Double): List<User> {
        return player.getBase().getNearbyEntities(
            radius,
            radius,
            radius
        ).filterIsInstance<Player>().map {
            userService.getUser(it)
        }
    }

    private fun getDistance(player1: User, player2: User): Double {

        val loc1 = player1.getBase().location

        val loc2 = player2.getBase().location


        val dx = loc2.x - loc1.x

        val dy = loc2.y - loc1.y

        val dz = loc2.z - loc1.z


        return sqrt(dx * dx + dy * dy + dz * dz)

    }

    fun getDirection(player1: Player, player2: Player): MiniMessageComponent {

        val direction = player1.location.direction.normalize()

        val player2Location = player2.location.toVector()

        val vectorToPlayer2 = player2Location.subtract(player1.location.toVector()).normalize()

        val angle = direction.angle(vectorToPlayer2)

        val isRight = direction.x * vectorToPlayer2.z - direction.z * vectorToPlayer2.x > 0

        return when {
            angle < Math.toRadians(45.0) -> format.direction.south
            angle > Math.toRadians(135.0) -> format.direction.north
            isRight -> format.direction.east
            else -> format.direction.west
        }
    }

//    fun getDirection(player1: Player, player2: Player): MiniMessageComponent {
//
//        val direction = player1.location.direction.normalize()
//
//        val player2Location = player2.location.toVector()
//
//        val vectorToPlayer2 = player2Location.subtract(player1.location.toVector()).normalize()
//
//        val angle = direction.angle(vectorToPlayer2)
//
//        val isRight = direction.x * vectorToPlayer2.z - direction.z * vectorToPlayer2.x > 0
//
//        return when {
//            angle < Math.toRadians(45.0) -> "↑"
//            angle > Math.toRadians(135.0) -> "↓"
//            isRight -> "→"
//            else -> "←"
//        }
//    }


}