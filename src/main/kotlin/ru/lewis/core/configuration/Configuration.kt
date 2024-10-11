package ru.lewis.core.configuration

import org.bukkit.Sound
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.boomearo.langhelper.versions.LangType
import ru.lewis.core.configuration.type.SoundConfiguration
import java.time.Duration

@ConfigSerializable
data class Configuration(

    val language: LangType = LangType.RU_RU,

    val database: DatabaseConfiguration,
    val performance: Performance = Performance(),
    val spawnSettings: SpawnSettings = SpawnSettings(),

    val disableJoinMessage: Boolean = true,
    val disableQuitMessage: Boolean = true,
    val disableCrystalDamageToBlock: Boolean = true,
    val disableDisableCrystalDamageToItem: Boolean = true,
    val disableModernUseElytra: Boolean = true,

    val soundSettings: SoundSettings = SoundSettings(),

    val nearbySettings: NearbySettings = NearbySettings(),

    val limitSettings: LimitSettings = LimitSettings()

    ) {
    @ConfigSerializable
    data class DatabaseConfiguration(
        val address: String = "localhost",
        val port: Int = 3306,
        val database: String = "mydatabase",
        val user: String = "user",
        val password: String = "password",
        val parameters: List<String> = listOf("useServerPrepStmts=true")
    )

    @ConfigSerializable
    data class SoundSettings(
        val teleport: SoundConfiguration = SoundConfiguration(Sound.ENTITY_ENDERMAN_TELEPORT)
    )

    @ConfigSerializable
    data class Performance(
        val savePeriod: Duration = Duration.ofSeconds(30),
        val minSavePeriod: Duration = Duration.ofMinutes(1),
        val teleportPeriod: Duration = Duration.ofMinutes(2),
        val maxBulkSaves: Int = 5,
    )

    @ConfigSerializable
    data class SpawnSettings(
        var location: Location = Location(),
        var world: String = ""
    ) {

        @ConfigSerializable
        data class Location(
            var x: Double = 0.0,
            var y: Double = 0.0,
            var z: Double = 0.0,
            var pitch: Float = 0.0f,
            var yaw: Float = 0.0f
        )
    }

    @ConfigSerializable
    data class NearbySettings(

        val parameters: List<Setting> = listOf(
            Setting()
        )

    ) {
        @ConfigSerializable
        data class Setting(
            val radius: Int = 100,
            val permission: String = "group.admin"
        )
    }

    @ConfigSerializable
    data class LimitSettings(

        val homeLimit: HomeLimit = HomeLimit()

    ) {

        @ConfigSerializable
        data class HomeLimit(
            val default: Int = 5,
            val limitList: Map<String, Int> = mapOf(
                Pair(
                    "group.default",
                    10
                )
            )
        )

    }

}