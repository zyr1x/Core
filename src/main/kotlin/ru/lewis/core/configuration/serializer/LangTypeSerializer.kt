package ru.lewis.core.configuration.serializer

import com.google.inject.Inject
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.kotlin.extensions.set
import org.spongepowered.configurate.serialize.TypeSerializer
import ru.boomearo.langhelper.versions.LangType
import java.lang.reflect.Type

class LangTypeSerializer @Inject constructor() : TypeSerializer<LangType> {

    override fun deserialize(type: Type, node: ConfigurationNode): LangType? {
        return node.string?.let { LangType.valueOf(it) }
    }

    override fun serialize(type: Type, obj: LangType?, node: ConfigurationNode) {
        node.set(String::class, obj?.name)
    }
}