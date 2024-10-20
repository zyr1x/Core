package ru.lewis.core.service.game

import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.plugin.Plugin
import org.hibernate.SessionFactory
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.hibernate.SessionFactoryBuilder
import ru.lewis.core.model.hibernate.entity.PlayerDataHomeHibernateEntity
import ru.lewis.core.model.hibernate.entity.PlayerKitCooldownHibernateEntity
import ru.lewis.core.model.hibernate.entity.WarpDataHibernateEntity
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import ru.lewis.core.service.game.data.GameUserData
import ru.lewis.core.service.game.data.GameWarpData
import java.io.IOException
import java.nio.file.Path
import kotlin.time.Duration.Companion.seconds

@Singleton
class GameService @Inject constructor(
    private val plugin: Plugin,
    private val configurationService: ConfigurationService,
    private val assistedInjectFactories: AssistedInjectFactories
) : TerminableModule {

    private lateinit var player: SessionFactory
    private lateinit var warp: SessionFactory

    private val config get() = configurationService.config

    private lateinit var userData: GameUserData
    private lateinit var warpData: GameWarpData

    override fun setup(consumer: TerminableConsumer) {

        player = connectSQLite(
            buildFile(
                "player.db"
            )
        )

        warp = connectSQLite(
            buildFile(
                "warps.db"
            )
        )

        userData = assistedInjectFactories.createUserDataService(player)
        warpData = assistedInjectFactories.createWarpDataService(warp)

    }

    private fun parametersToString(parameters: List<String>): String {
        return parameters.joinToString(prefix = "?", separator = "&")
    }


    fun reset(user: User) {
        TODO()
    }

    fun getUserData(): GameUserData = userData

    fun getWarpData(): GameWarpData = warpData

    private fun connectMariaDB(): SessionFactory {

        return SessionFactoryBuilder.build {
            classLoader = plugin::class.java.classLoader

            val cfg = config.database

            user = cfg.user
            password = cfg.password
            driver = org.mariadb.jdbc.Driver::class
            url = "jdbc:mariadb://${cfg.address}:${cfg.port}/${cfg.database}${parametersToString(cfg.parameters)}"

            hikariProperties["maximumPoolSize"] = Runtime.getRuntime().availableProcessors().toString()
            hikariProperties["connectionTimeout"] = 10.seconds.inWholeMilliseconds.toString()
            hikariProperties["poolName"] = plugin.name.plus("/mariadb")

        }

    }

    private fun connectSQLite(path: Path): SessionFactory {

        return SessionFactoryBuilder.build {
            classLoader = plugin::class.java.classLoader

            val cfg = config.database

            driver = org.sqlite.JDBC::class
            url = "jdbc:sqlite:${path}"

            hikariProperties["maximumPoolSize"] = Runtime.getRuntime().availableProcessors().toString()
            hikariProperties["connectionTimeout"] = 10.seconds.inWholeMilliseconds.toString()
            hikariProperties["poolName"] = plugin.name.plus("/$path")

            register<PlayerDataHomeHibernateEntity>()
            register<WarpDataHibernateEntity>()
            register<PlayerKitCooldownHibernateEntity>()

        }

    }

    fun buildFile(name: String): Path {

        val path: Path = plugin.dataFolder.toPath().resolve(name)
        val file = path.toFile()

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    println("Создан новый файл базы данных: " + file.getAbsolutePath())
                } else {
                    println("Не удалось создать файл базы данных.")
                }
            } catch (exception: IOException) {
                System.err.println("Ошибка при создании файла базы данных: " + exception.message)
                exception.printStackTrace()
            }
        } else {
            println("Файл базы данных уже существует: " + file.getAbsolutePath())
        }

        return path
    }

}