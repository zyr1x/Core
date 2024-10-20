package ru.lewis.core.command.kit

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.core.configuration.type.ItemTemplate
import ru.lewis.core.configuration.type.KitTemplate
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService
import java.time.Duration

@Command(name = "kitcreate")
@Permission("core.command.kitcreate")
class KitCreateCommand @Inject constructor(
    private val configurationService: ConfigurationService
){

    private val messages get() = configurationService.messages.common.kitCreate
    private val kits get() = configurationService.kitsConfiguration.kits

    @Execute
    fun createKit(
        @Context sender: User,
        @Arg name: String,
        @Arg cooldown: Duration
        ) {

        if (kits.firstOrNull { it.name == name } != null) {
            sender.getBase().sendMessage(
                messages.error.isFound.resolve(
                    Placeholder.unparsed(
                        "kit",
                        name
                    )
                )
            )
            return
        }

        val items: MutableMap<String, ItemTemplate> = sender.getBase().inventory.contents
            .mapIndexedNotNull { index, itemStack ->
                itemStack?.let { index.toString() to ItemTemplate.fromItem(it) }
            }
            .toMap()
            .toMutableMap()

        val kitTemplate = KitTemplate(
            name,
            cooldown,
            items
        )

        kits.add(kitTemplate)

        configurationService.saveKits()

        sender.getBase().sendMessage(
            messages.info.feedBack.resolve(
                Placeholder.unparsed(
                    "kit",
                    name
                )
            )
        )

    }

}