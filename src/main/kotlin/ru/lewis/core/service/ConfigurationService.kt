package ru.lewis.core.service

import com.google.inject.Inject
import com.google.inject.Singleton
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.Material
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.slf4j.Logger
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.extensions.set
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import ru.boomearo.langhelper.versions.LangType
import ru.lewis.core.configuration.Configuration
import ru.lewis.core.configuration.FormatConfiguration
import ru.lewis.core.configuration.GuisConfiguration
import ru.lewis.core.configuration.MessagesConfiguration
import ru.lewis.core.configuration.serializer.*
import ru.lewis.core.configuration.type.BossBarConfiguration
import ru.lewis.core.configuration.type.MiniMessageComponent
import java.awt.Color
import java.time.Duration
import kotlin.io.path.*

@Singleton
class ConfigurationService @Inject constructor(
    private val plugin: Plugin,
    private val materialSerializer: MaterialSerializer,
    private val durationSerializer: DurationSerializer,
    private val miniMessageComponentSerializer: MiniMessageComponentSerializer,
    private val colorSerializer: ColorSerializer,
    private val potionEffectSerializer: PotionEffectSerializer,
    private val enchantmentSerializer: EnchantmentSerializer,
    private val bossBarConfigurationSerializer: BossBarConfigurationSerializer,
    private val attributeModifierSerializer: AttributeModifierSerializer,
    private val potionEffectTypeSerializer: PotionEffectTypeSerializer,
    private val langTypeSerializer: LangTypeSerializer,
    private val logger: Logger,
) : TerminableModule {

    lateinit var config: Configuration
        private set

    lateinit var format: FormatConfiguration
        private set

    lateinit var messages: MessagesConfiguration
        private set

    lateinit var guis: GuisConfiguration
        private set

    private val rootDirectory = Path("")
    private val settingsFile = plugin.dataFolder.toPath().resolve("settings.yml")
    private val guisFile = plugin.dataFolder.toPath().resolve("guis.yml")
    private val messagesFile = plugin.dataFolder.toPath().resolve("language/message_ru.yml")
    private val formatFile = plugin.dataFolder.toPath().resolve("format.yml")

    override fun setup(consumer: TerminableConsumer) = doReload()

    fun reload() = doReload()

    fun saveConfig() {
        createLoaderBuilder().path(settingsFile).build().let {
            it.save(it.createNode().set(config))
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    @Synchronized
    private fun doReload() {
        plugin.dataFolder.toPath().createDirectories()

        val builder = createLoaderBuilder()

        format = builder.path(formatFile).build().getAndSave<FormatConfiguration>()
        guis = builder.path(guisFile).build().getAndSave<GuisConfiguration>()
        config = builder.path(settingsFile).build().getAndSave<Configuration>()
        messages = builder.path(messagesFile).build().getAndSave<MessagesConfiguration>()
    }

    private fun createLoaderBuilder(): YamlConfigurationLoader.Builder {
        return YamlConfigurationLoader.builder()

            .defaultOptions {
                it.serializers { serializers ->
                    serializers
                        .register(MiniMessageComponent::class.java, miniMessageComponentSerializer)
                        .register(Duration::class.java, durationSerializer)
                        .register(LangType::class.java, langTypeSerializer)
                        .register(Material::class.java, materialSerializer)
                        .register(Color::class.java, colorSerializer)
                        .register(PotionEffect::class.java, potionEffectSerializer)
                        .register(Enchantment::class.java, enchantmentSerializer)
                        .register(BossBarConfiguration::class.java, bossBarConfigurationSerializer)
                        .register(AttributeModifier::class.java, attributeModifierSerializer)
                        .register(PotionEffectType::class.java, potionEffectTypeSerializer)
                        .registerAnnotatedObjects(objectMapperFactory())
                }
            }

            .indent(2)
            .nodeStyle(NodeStyle.BLOCK)
    }

    private inline fun <reified T : Any> YamlConfigurationLoader.getAndSave(): T {
        val obj = this.load().get(T::class)!!
        this.save(this.createNode().set(T::class, obj))
        return obj
    }

    //powered by BlackBoroness

}

