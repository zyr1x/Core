package ru.lewis.core.model

import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.hibernate.SessionFactory
import ru.lewis.core.model.menu.BackButton
import ru.lewis.core.model.menu.ForwardButton
import ru.lewis.core.model.templates.Home
import ru.lewis.core.model.templates.Warp
import ru.lewis.core.model.user.User
import ru.lewis.core.service.GlobalService
import ru.lewis.core.service.UserDataService
import ru.lewis.core.service.WarpDataService
import java.util.*

interface AssistedInjectFactories {

    fun createUser(
        offlinePlayer: OfflinePlayer,
        playerDataHomeActual: UserDataService.PlayerDataHomeActual
    ): User

    fun createUserDataService(
        sessionFactory: SessionFactory
    ): UserDataService

    fun createHome(
        name: String,
        location: Location
    ): Home

    fun createWarpDataService(
        sessionFactory: SessionFactory
    ): WarpDataService

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