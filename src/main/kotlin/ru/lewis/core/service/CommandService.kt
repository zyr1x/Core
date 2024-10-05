package ru.lewis.core.service

import dev.rollczi.litecommands.adventure.LiteAdventureExtension
import dev.rollczi.litecommands.argument.ArgumentKey
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages
import dev.rollczi.litecommands.suggestion.SuggestionResult
import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import net.kyori.adventure.text.minimessage.MiniMessage.miniMessage
import org.bukkit.GameMode
import org.bukkit.enchantments.Enchantment
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffectType
import ru.lewis.core.command.admin.ReloadCommand
import ru.lewis.core.command.features.*
import ru.lewis.core.command.argument.GamemodeArgument
import ru.lewis.core.command.gamemode.GamemodeCommand
import ru.lewis.core.command.inventory.*
import ru.lewis.core.command.item.EnchantCommand
import ru.lewis.core.command.argument.EnchantmentArgument
import ru.lewis.core.command.argument.PotionEffectTypeArgument
import ru.lewis.core.command.argument.UserArgument
import ru.lewis.core.command.context.UserContext
import ru.lewis.core.command.home.HomeCommand
import ru.lewis.core.command.home.HomeRemoveCommand
import ru.lewis.core.command.home.HomeSetCommand
import ru.lewis.core.command.item.ItemNameCommand
import ru.lewis.core.command.item.MoreCommand
import ru.lewis.core.command.item.PotionCommand
import ru.lewis.core.command.spawn.SetSpawnCommand
import ru.lewis.core.command.spawn.SpawnCommand
import ru.lewis.core.command.speed.FlySpeedCommand
import ru.lewis.core.command.speed.WalkSpeedCommand
import ru.lewis.core.command.teleport.TeleportCommand
import ru.lewis.core.command.teleport.TeleportHereCommand
import ru.lewis.core.command.teleport.TeleportLocationCommand
import ru.lewis.core.command.teleport.TeleportRequestCommand
import ru.lewis.core.command.time.DayCommand
import ru.lewis.core.command.time.NightCommand
import ru.lewis.core.command.weather.RainCommand
import ru.lewis.core.command.weather.StormCommand
import ru.lewis.core.command.weather.SunCommand
import ru.lewis.core.model.user.User

@Singleton
class CommandService @Inject constructor(
    private val plugin: Plugin,
    private val configurationService: ConfigurationService,

    /*
    * arguments
     */

    private val gamemodeArgument: GamemodeArgument,
    private val potionEffectTypeArgument: PotionEffectTypeArgument,
    private val enchantmentArgument: EnchantmentArgument,
    private val userArgument: UserArgument,

    /*
    * context
     */

    private val userContext: UserContext,

    /*
    * commands
     */

    private val homeCommand: HomeCommand,
    private val homeRemoveCommand: HomeRemoveCommand,
    private val homeSetCommand: HomeSetCommand,
    private val dayCommand: DayCommand,
    private val nightCommand: NightCommand,
    private val sunCommand: SunCommand,
    private val rainCommand: RainCommand,
    private val stormCommand: StormCommand,
    private val anvilCommand: AnvilCommand,
    private val cartographyTableCommand: CartographyTableCommand,
    private val clearInventoryCommand: ClearInventoryCommand,
    private val enderChestCommand: EnderChestCommand,
    private val fakeOpCommand: FakeOpCommand,
    private val grindStoneCommand: GrindStoneCommand,
    private val loomCommand: LoomCommand,
    private val smithingTableCommand: SmithingTableCommand,
    private val stoneCutterCommand: StoneCutterCommand,
    private val workBenchCommand: WorkBenchCommand,
    private val setSpawnCommand: SetSpawnCommand,
    private val spawnCommand: SpawnCommand,
    private val walkSpeedCommand: WalkSpeedCommand,
    private val flySpeedCommand: FlySpeedCommand,
    private val moreCommand: MoreCommand,
    private val itemNameCommand: ItemNameCommand,
    private val reloadCommand: ReloadCommand,
    private val godCommand: GodCommand,
    private val vanishCommand: VanishCommand,
    private val flyCommand: FlyCommand,
    private val effectCommand: EffectCommand,
    private val extCommand: ExtCommand,
    private val killCommand: KillCommand,
    private val teleportCommand: TeleportCommand,
    private val teleportHereCommand: TeleportHereCommand,
    private val teleportLocationCommand: TeleportLocationCommand,
    private val gamemodeCommand: GamemodeCommand,
    private val teleportRequestCommand: TeleportRequestCommand,
    private val healCommand: HealCommand,
    private val fixCommand: FixCommand,
    private val enchantCommand: EnchantCommand,
    private val suicideCommand: SuicideCommand,
    private val invseeCommand: InvseeCommand,
    private val potionCommand: PotionCommand,

    ) : TerminableModule {

    private val message get() = configurationService.messages.errors

    @Suppress("UnstableApiUsage")
    override fun setup(consumer: TerminableConsumer) {
        LiteBukkitFactory.builder(plugin.name, plugin)
            .commands(
                homeSetCommand,
                homeRemoveCommand,
                homeCommand,
                potionCommand,
                invseeCommand,
                suicideCommand,
                enchantCommand,
                fixCommand,
                healCommand,
                fakeOpCommand,
                teleportRequestCommand,
                gamemodeCommand,
                dayCommand,
                nightCommand,
                sunCommand,
                rainCommand,
                stormCommand,
                anvilCommand,
                cartographyTableCommand,
                clearInventoryCommand,
                enderChestCommand,
                grindStoneCommand,
                loomCommand,
                smithingTableCommand,
                stoneCutterCommand,
                workBenchCommand,
                setSpawnCommand,
                spawnCommand,
                walkSpeedCommand,
                flySpeedCommand,
                itemNameCommand,
                moreCommand,
                reloadCommand,
                godCommand,
                vanishCommand,
                flyCommand,
                killCommand,
                extCommand,
                effectCommand,
                teleportCommand,
                teleportHereCommand,
                teleportLocationCommand,
            )

            .argument(GameMode::class.java, gamemodeArgument)
            .argument(PotionEffectType::class.java, potionEffectTypeArgument)
            .argument(Enchantment::class.java, enchantmentArgument)
            .argument(User::class.java, userArgument)

            .argumentSuggestion(Int::class.java, ArgumentKey.of("1..10"),
                SuggestionResult.of(
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "10"))

            .context(User::class.java, userContext)

            .message(LiteBukkitMessages.INVALID_USAGE) { message.invalidUsage.asComponent() }
            .message(LiteBukkitMessages.MISSING_PERMISSIONS) { message.noPermission.asComponent() }
            .message(LiteBukkitMessages.PLAYER_NOT_FOUND) { message.playerNotFound.asComponent() }
            .message(LiteBukkitMessages.INVALID_NUMBER) { message.invalidNumber.asComponent() }

            .extension(LiteAdventureExtension()) { config ->
                config.miniMessage(true)
                config.legacyColor(true)
                config.colorizeArgument(true)
                config.serializer(miniMessage())
            }
            .build()

    }
}