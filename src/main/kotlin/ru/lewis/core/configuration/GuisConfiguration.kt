package ru.lewis.core.configuration

import org.bukkit.Material
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.lewis.core.configuration.type.ItemTemplate
import ru.lewis.core.configuration.type.MenuConfig
import ru.lewis.core.configuration.type.MiniMessageComponent
import ru.lewis.core.extension.asMiniMessageComponent

@ConfigSerializable
data class GuisConfiguration(

    val guis: Guis = Guis(),
    val buttons: Buttons = Buttons()

) {

    @ConfigSerializable
    data class Guis(

        val inventory: Inventory = Inventory(),
        val warpList: WarpList = WarpList(),
        val homeList: HomeList = HomeList()

    ) {

        @ConfigSerializable
        data class Inventory(
            val template: MenuConfig = MenuConfig(
                title = "Инвентарь игрока <player>".asMiniMessageComponent(),
                structure = listOf(
                    "# H C L B # M O #",
                    "# # # # # # # # #",
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    ". . . . . . . . ."
                ),
                customItems = mapOf(
                    Pair(
                        '#',
                        ItemTemplate(Material.RED_STAINED_GLASS_PANE)
                    )
                ),
                templates = mapOf(
                    Pair(
                        'H',
                        ItemTemplate(Material.BARRIER)
                    ),
                    Pair(
                        'C',
                        ItemTemplate(Material.BARRIER)
                    ),
                    Pair(
                        'L',
                        ItemTemplate(Material.BARRIER)
                    ),
                    Pair(
                        'B',
                        ItemTemplate(Material.BARRIER)
                    ),
                    Pair(
                        'M',
                        ItemTemplate(Material.BARRIER)
                    ),
                    Pair(
                        'O',
                        ItemTemplate(Material.BARRIER)
                    )
                )
            ),
        )

        @ConfigSerializable
        data class WarpList(
            val template: MenuConfig = MenuConfig(
                title = "Список варпов".asMiniMessageComponent(),
                structure = listOf(
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    "# # # # # # # # #",
                    "x x x < U > x x x"
                ),
                customItems = mapOf(
                    Pair(
                        '#',
                        ItemTemplate(Material.RED_STAINED_GLASS_PANE)
                    )
                ),
            ),
        )

        @ConfigSerializable
        data class HomeList(
            val template: MenuConfig = MenuConfig(
                title = "Список домов".asMiniMessageComponent(),
                structure = listOf(
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    "# # # # # # # # #",
                    "x x x < x > x x x"
                ),
                customItems = mapOf(
                    Pair(
                        '#',
                        ItemTemplate(Material.RED_STAINED_GLASS_PANE)
                    )
                ),
            ),

            val homeItemTemplate: ItemTemplate = ItemTemplate(
                Material.GREEN_SHULKER_BOX,
                displayName = "Дом: <name>".asMiniMessageComponent(),
                lore = listOf(
                    "<green>Нажмите, чтобы телепортироваться".asMiniMessageComponent()
                )
            )
        )

    }

    @ConfigSerializable
    data class Buttons(
        val switchPage: SwitchPage = SwitchPage()
    ) {

        @ConfigSerializable
        data class SwitchPage(
            val previous: Map<String, ItemTemplate> = mapOf(
                Pair(
                    "yes",
                    ItemTemplate(
                        Material.ENDER_EYE
                    )
                ),
                Pair(
                    "no",
                    ItemTemplate(
                        Material.ENDER_PEARL
                    )
                )
            ),

            val next: Map<String, ItemTemplate> = mapOf(
                Pair(
                    "yes",
                    ItemTemplate(
                        Material.ENDER_EYE
                    )
                ),
                Pair(
                    "no",
                    ItemTemplate(
                        Material.ENDER_PEARL
                    )
                )
            ),

            val refresh: Map<String, ItemTemplate> = mapOf(
                Pair(
                    "loading",
                    ItemTemplate(
                        Material.BARRIER
                    )
                ),
                Pair(
                    "loaded",
                    ItemTemplate(
                        Material.GREEN_WOOL
                    )
                )
            )
        )

    }

}