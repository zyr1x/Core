package ru.lewis.core.command.teleport.request

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
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem
import xyz.xenondevs.invui.window.Window
import xyz.xenondevs.invui.window.type.context.setTitle

@Command(name = "tpyes", aliases = ["tpaccept", "tps"])
class TeleportRequestAcceptCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val assistedInjectFactories: AssistedInjectFactories
) {

    private val messages get() = configurationService.messages.common
    private val sounds get() = configurationService.config.soundSettings
    private val gui get() = configurationService.guis.guis.teleportRequestsList

    @Execute
    fun acceptRequest(
        @Context sender: User,
        @Arg target: User
    ) {

        if (!sender.teleportRequests().contains(target)) {
            sender.getBase().sendMessage(
                messages.teleportRequestAccept.error.playerNotFound.resolve(
                    Placeholder.unparsed(
                        "player",
                        target.getName()
                    )
                )
            )
            return
        }

        this.accept(
            sender,
            target
        )

    }

    @Execute
    fun noArgs(
        @Context sender: User
    ) {
        openMenu(sender)
    }

    private fun accept(sender: User, target: User) {

        sender.teleportRequests().remove(target)

        target.getBase().teleportAsync(sender.getBase().location).thenRun {
            sounds.teleport.play(target.getBase())
        }

        sender.getBase().sendMessage(
            messages.teleportRequestAccept.info.feedBack.resolve(
                Placeholder.unparsed(
                    "player",
                    target.getName()
                )
            )
        )

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
                    user.teleportRequests().map { request ->
                        RequestButton(
                            user,
                            request
                        )
                    }
                )

            }

            setTitle(gui.template.title.asComponent())

        }.open(user.getBase())

    }

    inner class RequestButton(
        private val sender: User,
        private val request: User
    ) : AbstractItem() {

        override fun getItemProvider(): ItemProvider {
            return ItemBuilder(
                gui.playerItemTemplate.resolve(
                    Placeholder.unparsed(
                        "player",
                        request.getName()
                    )
                ).toItem()
            )
        }

        override fun handleClick(clickType: ClickType, player: Player, p2: InventoryClickEvent) {

            if (clickType.isLeftClick) {

                player.openInventory.close()

                accept(
                    sender,
                    request
                )

            } else if (clickType.isRightClick) {

                sender.teleportRequests().remove(request)

                openMenu(sender)

            }

        }

    }

}