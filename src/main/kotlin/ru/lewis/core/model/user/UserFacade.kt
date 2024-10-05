package ru.lewis.core.model.user

import com.google.inject.assistedinject.Assisted
import jakarta.inject.Inject
import org.bukkit.entity.Player
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.service.UserDataService
import java.math.BigDecimal

class UserFacade @Inject constructor(

    private val userDataService: UserDataService,

    @Assisted private val player: Player,
    @Assisted private val playerDataHomeActual: UserDataService.PlayerDataHomeActual = userDataService.PlayerDataHomeActual(player)

) : UserData(player, playerDataHomeActual), User {

    override fun reset() {
        userDataService.reset(this)
    }

    private val teleportRequests: MutableList<User> = mutableListOf()

    override fun isVanished(): Boolean = this.getBase().isInvisible

    override fun setVanished(value: Boolean) {
        this.getBase().isInvisible = value
    }

    override fun isGod(): Boolean = this.getBase().isInvulnerable

    override fun setGod(value: Boolean) {
        this.getBase().isInvulnerable = value
    }

    override fun teleportRequests(): List<User> = teleportRequests

    override fun sendRequest(user: User): Boolean {
        teleportRequests.add(user)

        return true
    }

    override fun deleteRequest(user: User): Boolean {
        teleportRequests.remove(user)

        return true
    }


}