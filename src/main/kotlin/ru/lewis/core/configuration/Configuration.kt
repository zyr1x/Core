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
    val nearSettings: NearSettings = NearSettings(),

    val disableJoinMessage: Boolean = true,
    val disableQuitMessage: Boolean = true,
    val disableCrystalDamageToBlock: Boolean = true,
    val disableDisableCrystalDamageToItem: Boolean = true,
    val disableModernUseElytra: Boolean = true,

    val soundSettings: SoundSettings = SoundSettings()

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
    data class NearSettings(
        val permissions: List<Permission> = listOf(Permission())
    ) {

        @ConfigSerializable
        data class Permission(
            val name : String = "near.1",
            val radius: Int = 300
        )

    }
}