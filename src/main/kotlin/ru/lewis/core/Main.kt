package ru.lewis.core

import com.google.inject.Inject
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.Plugin
import org.slf4j.Logger
import ru.lewis.core.model.SmartLifoCompositeTerminable
import ru.lewis.core.model.manager.UserManager
import ru.lewis.core.service.*
import ru.lewis.core.service.game.GameService
import xyz.xenondevs.invui.InvUI

class Main @Inject constructor(

    private val configurationService: ConfigurationService,
    private val userDataService: GameService,
    private val userService: UserManager,
    private val commandService: CommandService,
    private val bukkitAudiences: BukkitAudiences,
    private val plugin: Plugin,
    logger: Logger,

    ) {

    private val terminableRegistry = SmartLifoCompositeTerminable(logger)

    fun start() {
        audiences = bukkitAudiences
        InvUI.getInstance().setPlugin(plugin)

        terminableRegistry.apply {
            with(bukkitAudiences)
            bindModule(configurationService)
            bindModule(commandService)
            bindModule(userService)
            bindModule(userDataService)
        }
    }

    fun stop() {
        terminableRegistry.closeAndReportException()
    }

    companion object {
        lateinit var audiences: BukkitAudiences
    }
}
