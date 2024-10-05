//package ru.lewis.core.command.features
//
//import dev.rollczi.litecommands.annotations.command.Command
//import dev.rollczi.litecommands.annotations.context.Context
//import dev.rollczi.litecommands.annotations.execute.Execute
//import io.github.whyzervellasskx.jetcommons.configuration.type.MiniMessageComponent
//import io.github.whyzervellasskx.jetcommons.extension.asMiniMessageComponent
//import io.github.whyzervellasskx.jetcommons.service.ConfigurationService
//import jakarta.inject.Inject
//import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
//import org.bukkit.Location
//import org.bukkit.entity.Player
//
//@Command(name = "near")
//class NearCommand @Inject constructor(
//    private val configurationService: ConfigurationService
//){
//
//    private val messages get() = configurationService.messages.common.near
//    private val nearSettings get() = configurationService.config.nearSettings
//
//    @Execute
//    fun execute(@Context sender: Player) {
//
//        for (permission in nearSettings.permissions) {
//
//            if (!sender.hasPermission(permission.name)) {
//                continue
//            }
//
//            val location = sender.location
//            val radius = permission.radius
//            val nearbyPlayers = this.getNearbyPlayers(radius, location)
//
//            for (target in nearbyPlayers) {
//
//                val position = this.getPosition(location, target.location)
//
//                sender.sendMessage(
//                    this.messages.format.resolve(
//                        Placeholder.unparsed(
//                            "player",
//                            target.name
//                        ),
//                        Placeholder.unparsed(
//                            "radius",
//                            radius.toString()
//                        ),
//                        Placeholder.component(
//                            "position",
//                            position
//                        )
//                    )
//                )
//
//            }
//
//            return
//        }
//
//        sender.sendMessage("Для вашей привилегии не существует настройки. Если вы увидели это сообщение, обратитесь к администрации проекта.")
//
//    }
//
//    fun getNearbyPlayers(radius: Int, center: Location): MutableList<Player> =
//        center.getNearbyPlayers(radius.toDouble(), radius.toDouble(), radius.toDouble()).toMutableList()
//
//    fun getPosition(center: Location, target: Location): MiniMessageComponent = "asd".asMiniMessageComponent()
//
//}