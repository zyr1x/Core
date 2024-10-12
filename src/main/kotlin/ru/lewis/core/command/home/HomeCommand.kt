package ru.lewis.core.command.home

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import ru.lewis.core.configuration.type.import
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.templates.Home
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem
import xyz.xenondevs.invui.window.Window
import xyz.xenondevs.invui.window.type.context.setTitle

@Command(name = "home", aliases = ["homes", "dom", "dom2"])
class HomeCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val assistedInjectFactories: AssistedInjectFactories
){

    private val config get() = configurationService.config
    private val messages get() = configurationService.messages.common.home
    private val gui get() = configurationService.guis.guis.homeList

    @Execute
    fun teleportHome(
        @Context user: User,
        @Arg home: Home
    ) {

        teleport(user.getBase(), home)

    }

    @Execute
    fun openHomeList(
        @Context user: User
    ) {
        this.openMenu(user)
    }

    private fun teleport(player: Player, home: Home) {

        player.sendMessage(
            messages.info.feedBack.resolve(
                Placeholder.unparsed(
                    "name",
                    home.getName()
                )
            )
        )

        player.teleportAsync(home.getLocation()).thenRun {
            config.soundSettings.teleport.play(player)
        }

    }

    private fun openMenu(user: User) {

        Window.single().apply {

            import(gui.template) {

                addIngredient(
                    '<',
                    assistedInjectFactories.createBackButton()
                )

                addIngredient(
                    '>',
                    assistedInjectFactories.createForwardButton()
                )

                setContent(
                    user.getHomes().map { homeName ->
                        HomeButton(
                            user,
                            homeName
                        )
                    }
                )

            }

            setTitle(gui.template.title.asComponent())

        }.open(user.getBase())

    }

    private inner class HomeButton(
        private val user: User,
        private val home: Home,
    ): AbstractItem() {

        override fun getItemProvider(): ItemProvider {
            return ItemBuilder(
                gui.homeItemTemplate.resolve(
                    Placeholder.unparsed(
                        "name",
                        home.getName()
                    )
                ).toItem()
            )
        }

        override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
            if (clickType.isLeftClick) {
                player.openInventory.close()
                teleport(player, home)
            } else if (clickType.isRightClick) {
                user.delHome(home.getName())
                openMenu(user)
            }
        }

    }

}