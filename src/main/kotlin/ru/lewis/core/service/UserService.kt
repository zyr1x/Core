package ru.lewis.core.service

import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.Events
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerQuitEvent
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.user.User

@Singleton
class UserService @Inject constructor(
    private val assistedInjectFactories: AssistedInjectFactories,
    private val userDataService: UserDataService
) : TerminableModule {

    override fun setup(consumer: TerminableConsumer) {

        Events.subscribe(PlayerQuitEvent::class.java).handler { event ->
            val user = getUser(event.player)
            users.remove(user)
        }

    }

    private val users: MutableList<User> = mutableListOf()

    fun getUser(player: Player): User {
        return this.getUserOrCreate(player)
    }

    private fun getUserOrCreate(player: Player): User {

        for (user in users) {

            if (user.getUUID() == player.uniqueId) {

                return user

            }

        }

        val playerDataHomeActual: UserDataService.PlayerDataHomeActual = userDataService.PlayerDataHomeActual(player)
        val user = assistedInjectFactories.createUser(player, playerDataHomeActual)
        users.add(user)

        return user
    }

    fun getUsers(): List<User> = users

}