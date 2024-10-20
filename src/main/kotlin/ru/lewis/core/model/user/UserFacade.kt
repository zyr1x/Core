package ru.lewis.core.model.user

import com.google.inject.assistedinject.Assisted
import jakarta.inject.Inject
import org.bukkit.OfflinePlayer
import ru.lewis.core.service.game.GameService
import ru.lewis.core.service.game.data.GameUserData

class UserFacade @Inject constructor(

    private val gameService: GameService,

    @Assisted private val offlinePlayer: OfflinePlayer,
    @Assisted private val playerDataHomeActual: GameUserData.PlayerDataHomeActual,
    @Assisted private val playerKitDataCooldownActual: GameUserData.PlayerKitDataCooldownActual

) : UserData(offlinePlayer, playerDataHomeActual, playerKitDataCooldownActual, gameService.getWarpData()), User {

    override fun reset() {
        gameService.reset(this)
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

    override fun teleportRequests(): MutableList<User> = teleportRequests

    override fun sendRequest(user: User): Boolean {
        teleportRequests.add(user)

        return true
    }

    override fun deleteRequest(user: User): Boolean {
        teleportRequests.remove(user)

        return true
    }


}