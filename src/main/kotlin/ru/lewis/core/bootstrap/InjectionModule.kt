package ru.lewis.core.bootstrap

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.assistedinject.FactoryModuleBuilder
import jakarta.inject.Singleton
import me.lucko.helper.plugin.HelperPlugin
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Server
import org.bukkit.plugin.Plugin
import org.slf4j.Logger
import ru.boomearo.langhelper.LangHelper
import ru.boomearo.langhelper.versions.TranslateManager
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.templates.Home
import ru.lewis.core.model.templates.Warp
import ru.lewis.core.model.templates.impl.BasicHome
import ru.lewis.core.model.templates.impl.BasicWarp
import ru.lewis.core.model.user.User
import ru.lewis.core.model.user.UserFacade
import java.io.File

class InjectionModule(
    private val helperPlugin: HelperPlugin
) : AbstractModule() {

    override fun configure() {

        bind(Plugin::class.java).toInstance(helperPlugin)
        bind(TranslateManager::class.java).toInstance(LangHelper.getInstance().translateManager)

        install(
            FactoryModuleBuilder()
                .implement(User::class.java, UserFacade::class.java)
                .implement(Warp::class.java, BasicWarp::class.java)
                .implement(Home::class.java, BasicHome::class.java)
                .build(AssistedInjectFactories::class.java)
        )

    }

    @Singleton
    @Provides
    fun BukkitAudiences(plugin: Plugin): BukkitAudiences = BukkitAudiences.create(plugin)

    @Provides
    fun MiniMessage(): MiniMessage = MiniMessage.miniMessage()

    @Provides
    fun Server(plugin: Plugin): Server = plugin.server

    @Provides
    fun Logger(plugin: Plugin): Logger = plugin.slF4JLogger

    @Provides
    fun dataFolder(): File  = helperPlugin.dataFolder

}
