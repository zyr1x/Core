package ru.lewis.core.model.menu

import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.core.service.ConfigurationService
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.PageItem

class BackButton @Inject constructor(
    private val configurationService: ConfigurationService
): PageItem(false) {

    private val switchPage get() = configurationService.guis.buttons.switchPage

    override fun getItemProvider(p0: PagedGui<*>?): ItemProvider {
        return if (gui.hasPreviousPage()) {
            ItemBuilder(switchPage.previous["yes"]!!.resolve(
                Placeholder.unparsed(
                    "currentpage",
                    (gui.currentPage + 1).toString()
                ),
                Placeholder.unparsed(
                    "pageamount",
                    gui.pageAmount.toString()
                )
            ).toItem())
        } else {
            ItemBuilder(switchPage.previous["no"]!!.toItem())
        }
    }

}