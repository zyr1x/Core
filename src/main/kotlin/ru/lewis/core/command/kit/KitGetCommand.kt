package ru.lewis.core.command.kit

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import io.github.blackbaroness.durationserializer.DurationFormats
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import ru.lewis.core.configuration.type.KitTemplate
import ru.lewis.core.configuration.type.import
import ru.lewis.core.extension.format
import ru.lewis.core.model.AssistedInjectFactories
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AutoUpdateItem
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.window.Window
import xyz.xenondevs.invui.window.type.context.setTitle

@Command(name = "kit", aliases = ["kits"])
@Permission("core.command.kit")
class KitGetCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val assistedInjectFactories: AssistedInjectFactories
){

    private val kitsConfiguration get() = configurationService.kitsConfiguration
    private val errors get() = configurationService.messages.errors
    private val messages get() = configurationService.messages.common.kitGet
    private val kitGui get() = configurationService.guis.guis.kitList
    private val invGui get() = configurationService.guis.guis.kitItemsList

    @Execute
    fun noArgs(
        @Context sender: User
    ) {
        openMenu(sender)
    }

    @Execute(name = "preview")
    fun previewKit(
        @Context sender: User,
        @Arg kit: KitTemplate
    ) {
        preview(sender.getBase(), kit)
    }

    @Execute
    fun getKit(
        @Context sender: User,
        @Arg kit: KitTemplate
    ) {

        takeIt(sender, kit)

    }

    @Execute
    fun giveKit(
        @Context sender: CommandSender,
        @Arg kit: KitTemplate,
        @Arg target: User,
    ) {

        kit.items.forEach {
            target.getBase().inventory.addItem(it.value.toItem())
        }

        sender.sendMessage(
            messages.info.feedBackTarget.resolve(
                Placeholder.unparsed(
                    "player",
                    target.getName()
                ),
                Placeholder.unparsed(
                    "kit",
                    kit.name
                ),
            )
        )

        target.getBase().sendMessage(
            messages.info.feedBackToTarget.resolve(
                Placeholder.unparsed(
                    "player",
                    sender.getName()
                ),
                Placeholder.unparsed(
                    "kit",
                    kit.name
                )
            )
        )

    }

    private fun takeIt(user: User, kit: KitTemplate): Boolean {

        kit.permission?.let {
            if (!user.getBase().hasPermission(it)) {
                user.getBase().sendMessage(
                    errors.noPermission
                )
                return false
            }
        }

        if (!user.getBase().isOp && user.existsKitCooldown(kit)) {
            user.getBase().sendMessage(
                messages.error.cooldown.resolve(
                    Placeholder.unparsed(
                        "time",
                        user.getKitCooldown(kit).format(
                            DurationFormats.shortRussian()
                        )
                    )
                )
            )
            return false
        }

        kit.items.forEach {
            user.getBase().inventory.addItem(it.value.toItem())
        }

        user.getBase().sendMessage(
            messages.info.feedBack.resolve(
                Placeholder.unparsed(
                    "kit",
                    kit.name
                ),
            )
        )

        user.insertKitCooldown(kit)

        return true

    }

    private fun openMenu(user: User) {

        Window.single().apply {

            import(kitGui.template) {

                addIngredient(
                    '<',
                    assistedInjectFactories.createBackButton()
                )

                addIngredient(
                    '>',
                    assistedInjectFactories.createForwardButton()
                )

                setContent(
                    kitsConfiguration.kits.map { kit ->
                        KitButton(
                            kit,
                            user
                        )
                    }
                )

            }

            setTitle(kitGui.template.title.asComponent())

        }.open(user.getBase())

    }

    private fun preview(player: Player, kit: KitTemplate) {

        Window.single().apply {

            import(invGui.template) {

                addIngredient(
                    '<',
                    assistedInjectFactories.createBackButton()
                )

                addIngredient(
                    '>',
                    assistedInjectFactories.createForwardButton()
                )

                setContent(
                    kit.items.values.map {
                        SimpleItem(it.toItem())
                    }
                )

            }

            setTitle(invGui.template.title.resolve(
                Placeholder.unparsed(
                    "kit",
                    kit.name
                )
            ).asComponent())

        }.open(player)

    }

    inner class KitButton(

        private val kit: KitTemplate,
        private val user: User

    ) : AutoUpdateItem(20,

        {

            if (!user.existsKitCooldown(kit)) {

                ItemBuilder(
                    kit.availableItem
                        .resolve(
                            Placeholder.unparsed(
                                "kit",
                                kit.name
                            )
                        )
                        .toItem()
                )

            } else {

                ItemBuilder(
                    kitGui.notAvailableItem
                        .resolve(
                            Placeholder.unparsed(
                                "kit",
                                kit.name
                            ),
                            Placeholder.unparsed(
                                "time",
                                user.getKitCooldown(kit).format(
                                    DurationFormats.shortRussian()
                                )
                            )
                        )
                        .toItem()
                )

            }

        }

        ) {

        override fun handleClick(p0: ClickType, player: Player, p2: InventoryClickEvent) {

            if (p0.isLeftClick) {
                takeIt(user, kit)
                player.closeInventory()
            } else if (p0.isRightClick) {
                preview(player, kit)
            }

        }

    }

}