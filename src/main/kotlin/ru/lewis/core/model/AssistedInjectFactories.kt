package ru.lewis.core.model

import org.bukkit.entity.Player
import ru.lewis.core.model.menu.BackButton
import ru.lewis.core.model.menu.ForwardButton
import ru.lewis.core.model.user.User
import ru.lewis.core.model.user.UserData
import ru.lewis.core.service.UserDataService

interface AssistedInjectFactories {

    fun createUser(
        player: Player,
        playerDataHomeActual: UserDataService.PlayerDataHomeActual
    ): User

    fun createBackButton(
    ): BackButton

    fun createForwardButton(
    ): ForwardButton

}