package ru.lewis.core.model

import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.hibernate.SessionFactory
import ru.lewis.core.model.menu.BackButton
import ru.lewis.core.model.menu.ForwardButton
import ru.lewis.core.model.templates.Home
import ru.lewis.core.model.templates.Warp
import ru.lewis.core.model.user.User
import ru.lewis.core.service.game.data.GameUserData
import ru.lewis.core.service.game.data.GameWarpData
import java.util.*

interface AssistedInjectFactories {

    fun createUser(
        offlinePlayer: OfflinePlayer,
        playerDataHomeActual: GameUserData.PlayerDataHomeActual,
        playerDataKitCooldownActual: GameUserData.PlayerKitDataCooldownActual
    ): User

    fun createUserDataService(
        sessionFactory: SessionFactory
    ): GameUserData

    fun createHome(
        name: String,
        location: Location
    ): Home

    fun createWarpDataService(
        sessionFactory: SessionFactory
    ): GameWarpData

    fun createWarp(
        name: String,
        owner: UUID,
        location: Location
    ): Warp

    fun createBackButton(
    ): BackButton

    fun createForwardButton(
    ): ForwardButton

}