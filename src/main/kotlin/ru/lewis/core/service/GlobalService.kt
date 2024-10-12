package ru.lewis.core.service

import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.plugin.Plugin
import org.hibernate.SessionFactory
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.hibernate.SessionFactoryBuilder
import ru.lewis.core.model.hibernate.entity.PlayerDataHomeHibernateEntity
import ru.lewis.core.model.hibernate.entity.WarpDataHibernateEntity
import ru.lewis.core.model.user.User
import kotlin.time.Duration.Companion.seconds

@Singleton
class GlobalService @Inject constructor(
    private val plugin: Plugin,
    private val configurationService: ConfigurationService,
    private val assistedInjectFactories: AssistedInjectFactories
) : TerminableModule {

    private lateinit var sessionFactory: SessionFactory
    private val config get() = configurationService.config

    private lateinit var userDataService: UserDataService
    private lateinit var warpDataService: WarpDataService

    override fun setup(consumer: TerminableConsumer) {

        sessionFactory = SessionFactoryBuilder.build {
            classLoader = plugin::class.java.classLoader

            val cfg = config.database

            user = cfg.user
            password = cfg.password
            driver = org.mariadb.jdbc.Driver::class
            url = "jdbc:mariadb://${cfg.address}:${cfg.port}/${cfg.database}${parametersToString(cfg.parameters)}"

            hikariProperties["maximumPoolSize"] = Runtime.getRuntime().availableProcessors().toString()
            hikariProperties["connectionTimeout"] = 10.seconds.inWholeMilliseconds.toString()
            hikariProperties["poolName"] = plugin.name

            register<PlayerDataHomeHibernateEntity>()
            register<WarpDataHibernateEntity>()

        }
        consumer.bind(sessionFactory)

        userDataService = assistedInjectFactories.createUserDataService(sessionFactory)
        warpDataService = assistedInjectFactories.createWarpDataService(sessionFactory)

    }

    private fun parametersToString(parameters: List<String>): String {
        return parameters.joinToString(prefix = "?", separator = "&")
    }


    fun reset(user: User) {
        TODO()
    }

    fun getUserData(): UserDataService = userDataService

    fun getWarpData(): WarpDataService = warpDataService

}