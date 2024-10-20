package ru.lewis.core.model.manager

import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.Events
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.OfflinePlayer
import org.bukkit.event.player.PlayerQuitEvent
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.user.User
import ru.lewis.core.service.game.GameService
import ru.lewis.core.service.game.data.GameUserData

@Singleton
class UserManager @Inject constructor(
    private val assistedInjectFactories: AssistedInjectFactories,
    private val gameService: GameService
) : TerminableModule {

    override fun setup(consumer: TerminableConsumer) {

        Events.subscribe(PlayerQuitEvent::class.java).handler { event ->
            val user = getUser(event.player)
            users.remove(user)
        }

    }

    private val users: MutableList<User> = mutableListOf()

    fun getUser(offlinePlayer: OfflinePlayer): User {
        return this.getUserOrCreate(offlinePlayer)
    }

    private fun getUserOrCreate(offlinePlayer: OfflinePlayer): User {

        for (user in users) {

            if (user.getUUID() == offlinePlayer.uniqueId) {

                return user

            }

        }

        val playerDataHomeActual: GameUserData.PlayerDataHomeActual = gameService.getUserData().PlayerDataHomeActual(offlinePlayer)
        val playerDataKitCooldownActual: GameUserData.PlayerKitDataCooldownActual = gameService.getUserData().PlayerKitDataCooldownActual(offlinePlayer)

        val user = assistedInjectFactories.createUser(offlinePlayer, playerDataHomeActual, playerDataKitCooldownActual)
        users.add(user)

        return user
    }

    fun getUsers(): List<User> = users

}