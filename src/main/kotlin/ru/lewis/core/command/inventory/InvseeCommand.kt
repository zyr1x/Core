package ru.lewis.core.command.inventory

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.lewis.core.configuration.type.import
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.window.Window
import xyz.xenondevs.invui.window.type.context.setTitle

@Command(name = "invsee", aliases = ["invcheck"])
class InvseeCommand @Inject constructor(
    private val configurationService: ConfigurationService
){

    private val guisConfiguration get() = configurationService.guis
    private val gui get() = guisConfiguration.guis.inventory

    @Execute
    fun execute(@Context sender: User, @Arg target: User) {
        this.openMenu(sender, target)
    }

    private fun openMenu(viewer: User, target: User) {

        val inventory = target.getBase().inventory

        val helmet = inventory.helmet ?: gui.template.templates['H']!!.toItem()
        val chestplate = inventory.chestplate ?: gui.template.templates['C']!!.toItem()
        val leggings = inventory.leggings ?: gui.template.templates['L']!!.toItem()
        val boots = inventory.boots ?: gui.template.templates['B']!!.toItem()
        val itemInMainHand = if (inventory.itemInMainHand.type != Material.AIR) inventory.itemInMainHand else gui.template.templates['M']!!.toItem()
        val itemInOffHand = if (inventory.itemInOffHand.type != Material.AIR) inventory.itemInOffHand else gui.template.templates['O']!!.toItem()

        Window.single().apply {

            import(gui.template) {

                addIngredient('H', helmet)
                addIngredient('C', chestplate)
                addIngredient('L', leggings)
                addIngredient('B', boots)
                .addIngredient('M', itemInMainHand)
                .addIngredient('O', itemInOffHand)

                setContent(
                    inventory.storageContents.map {
                        SimpleItem(it ?: ItemStack(Material.AIR))
                    }
                )

            }

            setTitle(gui.template.title.resolve(
                Placeholder.unparsed(
                    "player",
                    target.getName()
                )
            ).asComponent())

        }.open(viewer.getBase())

    }

}