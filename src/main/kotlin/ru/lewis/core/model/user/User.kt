package ru.lewis.core.model.user

import com.google.inject.ImplementedBy
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import ru.lewis.core.model.templates.Home
import ru.lewis.core.model.templates.Warp
import java.util.UUID

@ImplementedBy(UserFacade::class)
interface User {

    /*
    * User
     */

    fun getBase(): Player
    fun getOfflinePlayer(): OfflinePlayer

    fun getName(): String
    fun getUUID(): UUID

    fun reset()

    fun isVanished(): Boolean
    fun setVanished(value: Boolean)

    fun isGod(): Boolean
    fun setGod(value: Boolean)

    fun teleportRequests(): List<User>
    fun sendRequest(user: User): Boolean
    fun deleteRequest(user: User): Boolean

    /*
     * UserData
     */

    fun getHome(name: String): Home?
    fun getHomes(): List<Home>
    fun setHome(name: String): Boolean
    fun delHome(name: String): Boolean
    fun hasHome(): Boolean

    fun getWarp(name: String): Warp?
    fun getWarps(): List<Warp>
    fun setWarp(name: String): Boolean
    fun delWarp(name: String): Boolean

//    fun getWarp(name: String): Location
//    fun getWarps(): List<String>
//    fun setWarp(name: String, location: Location)
//    fun delWarp(name: String): Boolean
//    fun renameWarp(oldName: String, newName: String): Boolean
//    fun hasWarp(): Boolean

    fun getLastLocation(): Location
    fun setLastLocation(location: Location)

    fun isAfk(): Boolean
    fun setAfk(value: Boolean)

}