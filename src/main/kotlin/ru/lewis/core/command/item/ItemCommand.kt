package ru.lewis.core.command.item

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ru.lewis.core.model.user.User

@Command(name = "item")
@Permission("core.command.item")
class ItemCommand @Inject constructor() {

    @Execute
    fun giveItem(@Context user: User, @Arg item: Material) {
        user.getBase().inventory.addItem(
            ItemStack(
                item
            )
        )
    }

    @Execute
    fun giveItem(@Context user: User, @Arg item: Material, @Arg amount: Int) {
        user.getBase().inventory.addItem(
            ItemStack(
                item,
                amount
            )
        )
    }

}