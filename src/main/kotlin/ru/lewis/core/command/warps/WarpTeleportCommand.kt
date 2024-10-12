package ru.lewis.core.command.warps

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import ru.lewis.core.configuration.type.import
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.templates.Warp
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import ru.lewis.core.service.GlobalService
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem
import xyz.xenondevs.invui.window.Window
import xyz.xenondevs.invui.window.type.context.setTitle

@Command(name = "warp", aliases = ["warps"])
class WarpTeleportCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val assistedInjectFactories: AssistedInjectFactories,
    private val globalService: GlobalService
){

    private val messages get() = configurationService.messages.common.warpTeleport
    private val sounds get() = configurationService.config.soundSettings
    private val gui get() = configurationService.guis.guis.warpList

    @Execute
    fun noArgs(
        @Context user: User
    ) {
        openMenu(user)
    }

    @Execute
    fun teleportWarp(
        @Context user: User,
        @Arg warp: Warp
    ) {

        teleport(user.getBase(), warp)

    }

    private fun openMenu(
        user: User
    ) {

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
                    globalService.getWarpData().getData().map { warp ->
                        WarpButton(
                            user,
                            warp
                        )
                    }
                )

            }

            setTitle(gui.template.title.asComponent())

        }.open(user.getBase())

    }

    private fun teleport(player: Player, warp: Warp) {

        player.sendMessage(
            messages.info.feedBack.resolve(
                Placeholder.unparsed(
                    "name",
                    warp.getName()
                )
            )
        )

        player.teleportAsync(warp.getLocation()).thenRun {
            sounds.teleport.play(player)
        }

    }

    private inner class WarpButton(
        private val user: User,
        private val warp: Warp,
    ): AbstractItem() {

        override fun getItemProvider(): ItemProvider {
            return ItemBuilder(
                gui.warpItemTemplate.resolve(
                    Placeholder.unparsed(
                        "name",
                        warp.getName()
                    ),
                    Placeholder.unparsed(
                        "owner",
                        user.getName()
                    )
                ).toItem()
            )
        }

        override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
            if (clickType.isLeftClick) {
                player.openInventory.close()
                teleport(player, warp)
            } else if (clickType.isRightClick) {

                if (player.isOp) {
                    user.delWarp(warp.getName())
                    return
                }

                if (warp.getOwner() == user.getUUID()) {
                    user.delWarp(warp.getName())
                }

            }
        }

    }

}