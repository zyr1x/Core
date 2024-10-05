package ru.lewis.core.extension

import io.github.blackbaroness.durationserializer.DurationFormats
import io.github.blackbaroness.durationserializer.format.DurationFormat
import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player
import ru.lewis.core.configuration.type.MiniMessageComponent
import java.time.temporal.ChronoUnit

fun placeholderApiTagResolver(player: Player?): TagResolver {
    return TagResolver.resolver("papi") { arguments, _ ->
        val placeholder = arguments.popOr("PAPI placeholder is missing").value()
        val resultRaw = PlaceholderAPI.setPlaceholders(player, "%$placeholder%")
        val resultComponent = LegacyComponentSerializer.legacySection().deserialize(resultRaw)
        return@resolver Tag.selfClosingInserting(resultComponent)
    }
}

fun ComponentLike.replace(what: String, with: String): Component {
    return asComponent().replaceText { builder -> builder.matchLiteral(what).replacement(with) }
}

fun ComponentLike.replace(what: String, with: ComponentLike): ComponentLike {
    return asComponent().replaceText { builder -> builder.matchLiteral(what).replacement(with) }
}

fun ComponentLike.legacy(): String {
    return LegacyComponentSerializer.legacySection().serialize(asComponent())
}

fun Component.toJson(): String {
    return GsonComponentSerializer.gson().serialize(this)
}

fun String.componentFromJson(): Component {
    return GsonComponentSerializer.gson().deserialize(this)
}

fun java.time.Duration.toPlaceholder(
    key: String,
    truncateTo: ChronoUnit = ChronoUnit.SECONDS,
    format: DurationFormat = DurationFormats.mediumLengthRussian()
): TagResolver {
    return Placeholder.unparsed(key, this.truncate(truncateTo).format(format))
}

fun String.asMiniMessageComponent(): MiniMessageComponent {
    return MiniMessageComponent(this, this.parseMiniMessage())
}

fun Component.asMiniMessageComponent(): MiniMessageComponent {
    return MiniMessageComponent(
        MiniMessage.miniMessage().serialize(this)
            .removePrefix("<!italic><!underlined><!strikethrough><!bold><!obfuscated>"), this
    )
}

fun String.parseMiniMessage(vararg tagResolvers: TagResolver): Component =
    MiniMessage.miniMessage().deserialize(this, *tagResolvers)