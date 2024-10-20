package ru.lewis.core.configuration

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.lewis.core.configuration.type.MiniMessageComponent
import ru.lewis.core.extension.asMiniMessageComponent

@ConfigSerializable
data class MessagesConfiguration(
    val common: CommonMessages = CommonMessages(),
    val errors: Errors = Errors(),
    val placeholders: Placeholders = Placeholders()
) {

    @ConfigSerializable
    data class CommonMessages(

        val anvil: AnvilMessage = AnvilMessage(),
        val cartographyTable: CartographyTableMessages = CartographyTableMessages(),
        val clearInventory: ClearInventoryMessage = ClearInventoryMessage(),
        val smithingTable: SmithingTableMessage = SmithingTableMessage(),
        val grindStone: GrindStoneMessages = GrindStoneMessages(),
        val workBench: WorkBenchMessage = WorkBenchMessage(),
        val effect: EffectMessage = EffectMessage(),
        val enchants: EnchantsMessage = EnchantsMessage(),
        val enderChest: EnderChestMessage = EnderChestMessage(),
        val ext: ExtMessage = ExtMessage(),
        val fly: FlyMessage = FlyMessage(),
        val flySpeed: FlySpeedMessage = FlySpeedMessage(),
        val heal: HealMessage = HealMessage(),
        val itemName: ItemNameMessage = ItemNameMessage(),
        val jump: JumpMessage = JumpMessage(),
        val kill: KillMessage = KillMessage(),
        val loom: LoomMessage = LoomMessage(),
        val more: MoreMessage = MoreMessage(),
        val walkSpeed: WalkSpeedMessages = WalkSpeedMessages(),
        val spawn: SpawnMessage = SpawnMessage(),
        val setSpawn: SetSpawnMessage = SetSpawnMessage(),
        val time: TimeMessage = TimeMessage(),
        val weather: WeatherMessage = WeatherMessage(),
        val gameMode: GameModeMessage = GameModeMessage(),
        val god: GodMessage = GodMessage(),
        val vanish: VanishMessage = VanishMessage(),
        val kit: KitMessage = KitMessage(),
        val fix: FixMessage = FixMessage(),
        val suicide: SuicideMessage = SuicideMessage(),
        val potion: PotionMessage = PotionMessage(),
        val home: HomeMessage = HomeMessage(),
        val homeSet: HomeSetMessage = HomeSetMessage(),
        val homeRemove: HomeRemoveMessage = HomeRemoveMessage(),
        val nearby: NearbyMessage = NearbyMessage(),
        val teleportPosition: TeleportPositionMessage = TeleportPositionMessage(),
        val teleportPlayer: TeleportPlayerMessage = TeleportPlayerMessage(),
        val warpSet: WarpSetMessages = WarpSetMessages(),
        val warpTeleport: WarpTeleportMessages = WarpTeleportMessages(),
        val warpRemove: WarpRemoveMessages = WarpRemoveMessages(),
        val tpHere: TeleportHereMessages = TeleportHereMessages(),
        val feed: FeedMessages = FeedMessages(),
        val teleportRequest: TeleportRequestMessages = TeleportRequestMessages(),
        val teleportRequestAccept: TeleportRequestAcceptMessages = TeleportRequestAcceptMessages(),
        val teleportRequestCancel: TeleportRequestCancelMessages = TeleportRequestCancelMessages(),
        val kitGet: KitGetMessages = KitGetMessages(),
        val kitCreate: KitCreateMessages = KitCreateMessages(),
        val kitRemove: KitRemoveMessages = KitRemoveMessages()
    )

    @ConfigSerializable
    data class NearbyMessage(
        val error: ErrorMessages = ErrorMessages(),
    ) {
        @ConfigSerializable
        data class ErrorMessages (
            val playersNotFound: MiniMessageComponent = "<red>Игроков рядом нет.".asMiniMessageComponent()
        )
    }

    @ConfigSerializable
    data class FeedMessages(
        val info: InfoMessages = InfoMessages()
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно утолили себе голод".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы утолили голод игроку <player>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам утолил голод игрок <player>".asMiniMessageComponent()
        )
    }

    @ConfigSerializable
    data class TeleportRequestCancelMessages(
        val info: InfoMessages = InfoMessages(),
        val error: ErrorMessages = ErrorMessages()
    ) {

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "Вы отменили запрос к игроку <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class ErrorMessages(
            val playerNotFound: MiniMessageComponent = "Вы не отправляли игроку <player> запросов!".asMiniMessageComponent()
        )

    }

    @ConfigSerializable
    data class KitRemoveMessages(
        val info: InfoMessages = InfoMessages(),
        val error: ErrorMessages = ErrorMessages()
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "".asMiniMessageComponent()
        )
        @ConfigSerializable
        data class ErrorMessages(
            val notFound: MiniMessageComponent = "Набор не найден".asMiniMessageComponent()
        )
    }

    @ConfigSerializable
    data class KitCreateMessages(
        val error: ErrorMessages = ErrorMessages(),
        val info: InfoMessages = InfoMessages()
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "".asMiniMessageComponent()
        )
        @ConfigSerializable
        data class ErrorMessages(
            val isFound: MiniMessageComponent = "".asMiniMessageComponent()
        )
    }

    @ConfigSerializable
    data class KitGetMessages(
        val info: InfoMessages = InfoMessages(),
        val error: ErrorMessages = ErrorMessages()
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "".asMiniMessageComponent()
        )
        @ConfigSerializable
        data class ErrorMessages(
            val notFound: MiniMessageComponent = "Набор не найден".asMiniMessageComponent(),
            val cooldown: MiniMessageComponent = "У вас задержка на набор: <time>".asMiniMessageComponent(),
        )
    }

    @ConfigSerializable
    data class TeleportRequestAcceptMessages(
        val info: InfoMessages = InfoMessages(),
        val error: ErrorMessages = ErrorMessages()
    ) {

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "Вы приняли запрос от игрока <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class ErrorMessages(
            val playerNotFound: MiniMessageComponent = "Игрок не найден в ваших запросах".asMiniMessageComponent()
        )

    }

    @ConfigSerializable
    data class TeleportRequestMessages(
        val info: InfoMessages = InfoMessages(),
        val error: ErrorMessages = ErrorMessages()
    ) {

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "Вы отправили запрос игроку <player>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "Вам отправил запрос на телепортацию игрок <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class ErrorMessages(
            val repeatRequest: MiniMessageComponent = "Вы уже отправляли запрос этому игроку!".asMiniMessageComponent()
        )

    }

    @ConfigSerializable
    data class TeleportHereMessages(
        val info: InfoMessages = InfoMessages()
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBackTarget: MiniMessageComponent = "Вы были успешно телепортированы к игроку <player>".asMiniMessageComponent(),
            val feedBack: MiniMessageComponent = "Вы успешно телепортировали игрока <player> к себе!".asMiniMessageComponent()
        )
    }

    // removeHome

    @ConfigSerializable
    data class HomeRemoveMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage(),
        val error: ErrorMessages = ErrorMessages()
    ) {

        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /removehome <name>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы удалили дом: <name></green>".asMiniMessageComponent(),
        )

        @ConfigSerializable
        data class ErrorMessages(
            val homeNotFound: MiniMessageComponent = "<red>Нельзя удалить не существующий дом :(".asMiniMessageComponent(),
        )

    }

    // setHome

    @ConfigSerializable
    data class HomeSetMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage(),
        val error: ErrorMessages = ErrorMessages()
    ) {

        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /sethome <name>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы установили дом: <name></green>".asMiniMessageComponent(),
        )

        @ConfigSerializable
        data class ErrorMessages(
            val homeIsFound: MiniMessageComponent = "<red>Нельзя перезаписать существующий дом :(".asMiniMessageComponent(),
            val limit: MiniMessageComponent = "<red>У вас лимит по установке домов.".asMiniMessageComponent()
        )

    }

    // home

    @ConfigSerializable
    data class HomeMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage(),
        val error: ErrorMessages = ErrorMessages()
    ) {

        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /home <name/list>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы телепортировались на дом: <name></green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно телепортировались на дом <name> игрока <player>".asMiniMessageComponent(),
        )

        @ConfigSerializable
        data class ErrorMessages(
            val homeNotFound: MiniMessageComponent = "<red>Такой дом небыл найден :(".asMiniMessageComponent(),
        )

    }

    // kit

    @ConfigSerializable
    data class KitMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage(),
        val error: ErrorMessages = ErrorMessages()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /kit <name> [player]".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы взяли набор <name></green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно выдали набор <name> игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам выдал набор <name> игрок <player> </green>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class ErrorMessages(
            val cooldown: MiniMessageComponent = "У вас имеется задержка на этот набор".asMiniMessageComponent(),
            val inventory: MiniMessageComponent = "У вас полный инвентарь! Нет Места.".asMiniMessageComponent(),
        )

    }

    @ConfigSerializable
    data class SuicideMessage(
        val info: InfoMessages = InfoMessages(),
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно умерли</green>".asMiniMessageComponent(),
        )
    }


    @ConfigSerializable
    data class AnvilMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /wb <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы открыли верстак</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно открыли инвентарь игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам открыл верстак игрок <player> </green>".asMiniMessageComponent()
        )
    }

    //cartograp

    @ConfigSerializable
    data class CartographyTableMessages(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /cartography <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы открыли стол картографа</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно открыли стол картографа игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам открыл стол картографа игрок <player> </green>".asMiniMessageComponent()
        )
    }

    //grindstone

    @ConfigSerializable
    data class GrindStoneMessages(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /grindstone <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы открыли стол grindstone</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешн игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам откррок <player> </green>".asMiniMessageComponent()
        )
    }

    //grindstone

    @ConfigSerializable
    data class StoneCutterMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /stonecutter <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы открыли стол stonecutter</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешн игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам откррок <player> </green>".asMiniMessageComponent()
        )
    }

    //smithing

    @ConfigSerializable
    data class SmithingTableMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /smithing <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы открыли стол smithing</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешн игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам откррок <player> </green>".asMiniMessageComponent()
        )
    }


    //clear inv

    @ConfigSerializable
    data class ClearInventoryMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /clear <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы очистили инвентарь</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно очистили инв игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам очистили инв игрок <player> </green>".asMiniMessageComponent()
        )
    }

    // workbench

    @ConfigSerializable
    data class WorkBenchMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /wb <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы открыли верстак</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно открыли инвентарь игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам открыл верстак игрок <player> </green>".asMiniMessageComponent()
        )
    }

    // effect

    @ConfigSerializable
    data class EffectMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /effect <effect> <duration> <amplifier>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно применили к себе эффект <effect> на время <duration> с уровнем <amplifier> </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно применили к игроку <player> эффект <effect> на время <duration> с уровнем <amplifier> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам успешно применили эффект <effect> на время <duration> с уровнем <amplifier> и сделал это <player></green>".asMiniMessageComponent()
        )
    }

    // enchants

    @ConfigSerializable
    data class EnchantsMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /enchant <enchantment> <level>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно наложили зачарование <enchant> с уровнем <level> </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно наложили игроку <player> зачарование <enchant> с уровнем <level> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам успешно наложили зачарование <enchant> с уровнем <level> и сделал это <player> </green>".asMiniMessageComponent()
        )
    }

    //enderchest

    @ConfigSerializable
    data class EnderChestMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /ec".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно открыли эндер сундук".asMiniMessageComponent(),
        )
    }


    //ext message

    @ConfigSerializable
    data class ExtMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /ext".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно потушили себя</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно потушили игрока <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вас успешно потушил игрок <player> </green>".asMiniMessageComponent()
        )
    }


    // fly

    @ConfigSerializable
    data class FlyMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /fly <player> [boolean]".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно установили себе новый режим флая <status> </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно установили игроку <player> новый режим флая  <status></green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам успешно наложили новый режим флая <status> сделал это <player> </green>".asMiniMessageComponent()
        )
    }



    // fly speed

    @ConfigSerializable
    data class FlySpeedMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /fly <player> [boolean]".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успещнл установили скорость полета <speed> </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успещнл установили скорость полета <speed> игроку <player>  <status></green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам успещнл установили скорость полета <speed> а сделал это <player> </green>".asMiniMessageComponent()
        )
    }

    @ConfigSerializable
    data class WarpRemoveMessages(
        val info: InfoMessages = InfoMessages(),
        val errors: ErrorMessages = ErrorMessages()
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val errors: ErrorMessages = ErrorMessages(),
            val feedBack: MiniMessageComponent = "<green>вы удалили варп <name>!".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class ErrorMessages(
            val warpNotFound: MiniMessageComponent = "<red>Такого варпа не существует!".asMiniMessageComponent(),
            val notOwned: MiniMessageComponent = "<red>Вы не являетесь владельцем варпа!".asMiniMessageComponent()
        )
    }

    @ConfigSerializable
    data class WarpTeleportMessages(
        val info: InfoMessages = InfoMessages(),
        val errors: ErrorMessages = ErrorMessages()
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно телепортировались на варп <name>".asMiniMessageComponent()
        )
        @ConfigSerializable
        data class ErrorMessages(
            val warpNotFound: MiniMessageComponent = "<red>Такого варпа не существует!".asMiniMessageComponent()
        )
    }

    @ConfigSerializable
    data class WarpSetMessages(
        val errors: ErrorMessages = ErrorMessages(),
        val info: InfoMessages = InfoMessages()
    ) {

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Варп <name> успешно установлен!".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class ErrorMessages(
            val warpIsFound: MiniMessageComponent = "<red>Такой варп уже существует, перезаписать не получится.".asMiniMessageComponent()
        )

    }

    // fly

    @ConfigSerializable
    data class TeleportPositionMessage(
        val info: InfoMessages = InfoMessages(),
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно телепортировались на указанные координаты".asMiniMessageComponent(),
        )
    }

    @ConfigSerializable
    data class TeleportPlayerMessage(
        val info: InfoMessages = InfoMessages()
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "Вы телепортировались к игроку <player>".asMiniMessageComponent()
        )
    }

    // fix

    @ConfigSerializable
    data class FixMessage(
        val info: InfoMessages = InfoMessages(),
    ) {
        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно починили <amount> предметов.</green>".asMiniMessageComponent(),
        )
    }


    // vanish

    @ConfigSerializable
    data class VanishMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /<vanish/v> <player> [boolean]".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно установили себе новый режим <status> </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно установили игроку <player> новый режим </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам успешно наложили наложили новый режим <status> (<player>) </green>".asMiniMessageComponent()
        )
    }

    // gamemode

    @ConfigSerializable
    data class GameModeMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /gamemode <value>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно установили себе новый игровой режим <gamemode> </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно установили игроку <player> новый игровой режим <gamemode></green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам успешно наложили наложили новый игровой режим <gamemode> (<player>) </green>".asMiniMessageComponent()
        )
    }

    // god

    @ConfigSerializable
    data class GodMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /god <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно установили себе новый режим <status> </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно установили игроку <player> новый режим <status></green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам успешно наложили наложили новый режим <state> (<player>) </green>".asMiniMessageComponent()
        )
    }


    // healed

    @ConfigSerializable
    data class HealMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /heal <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно исцелили себя </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно исцелили игрока <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вас успешно исцелил игрок <player> </green>".asMiniMessageComponent()
        )
    }


    // iname

    @ConfigSerializable
    data class ItemNameMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /iname <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно переименовали предмет: <newname> </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно переименовали предмет: <newname> игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вы успешно переименовали предмет: <newname> сделал это <player> </green>".asMiniMessageComponent()
        )
    }


    // jump

    @ConfigSerializable
    data class JumpMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /jump <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно прыгнули </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Игрок <player> прыгнул </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вас принудили прыгнуть сделал это <player> </green>".asMiniMessageComponent()
        )
    }


    // kill

    @ConfigSerializable
    data class KillMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /kill <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно убили <player></green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Ваc убил <player></green>".asMiniMessageComponent()
        )
    }

    // potion
    @ConfigSerializable
    data class PotionMessage(

        val info: InfoMessages = InfoMessages(),
        val error: ErrorMessages = ErrorMessages(),
        val help: HelpMessages = HelpMessages()

    ) {

        @ConfigSerializable
        data class HelpMessages(
            val info: MiniMessageComponent = "Использование команды: /potion <potion> <duration> <amplifier>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "Вы успешно наложили эффект <potion> на предмет.".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "Вы успешно наложили эффект <potion> на предмет игрока <player>.".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "Игрок <player> наложил эффект <potion> на ваш предмет в руках.".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class ErrorMessages(
            val notValidType: MiniMessageComponent = "Эффект можно наложить только на стрелы/зелья".asMiniMessageComponent()
        )

    }


    // loom

    @ConfigSerializable
    data class LoomMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /loom <player>".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы открыли хуйню</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успешно открыли хуйню игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам открыл хуйню игрок <player> </green>".asMiniMessageComponent()
        )
    }
    // more

    @ConfigSerializable
    data class MoreMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /more".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы стакнули предмет</green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы стакнули предмет игроку <player> </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам станклуи предмет и сделал это <player> </green>".asMiniMessageComponent()
        )
    }

    // walk speed

    @ConfigSerializable
    data class WalkSpeedMessages(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /speed <player> [boolean]".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успещнл установили ходьбы полета <speed> </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>Вы успещнл установили скорость ходьбы <speed> игроку <player>  <status></green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вам успещнл установили скорость ходьбы <speed> а сделал это <player> </green>".asMiniMessageComponent()
        )
    }


    // spawn

    @ConfigSerializable
    data class SpawnMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /spawn".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно телепортировались на спавн </green>".asMiniMessageComponent(),
            val feedBackToTarget: MiniMessageComponent = "<green>вы успешно телепортировали игрока <player> на спавн </green>".asMiniMessageComponent(),
            val feedBackTarget: MiniMessageComponent = "<green>Вас телепортировали на спавн а сделал это <player> </green>".asMiniMessageComponent()
        )
    }

    // spawn

    @ConfigSerializable
    data class SetSpawnMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val info: MiniMessageComponent = "Использование команды: /setspawn".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val feedBack: MiniMessageComponent = "<green>Вы успешно установили спавн: <world> <x> <y> <z></green>".asMiniMessageComponent(),
        )
    }

    //time

    @ConfigSerializable
    data class TimeMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val infoDay: MiniMessageComponent = "Использование команды: /day".asMiniMessageComponent(),
            val infoNight: MiniMessageComponent = "Использование команды: /day".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val day: MiniMessageComponent = "<green>Вы установили день в мире: <world> </green>".asMiniMessageComponent(),
            val night: MiniMessageComponent = "<green>Вы установили ночь в мире: <world> </green>".asMiniMessageComponent(),
        )
    }


    //weather

    @ConfigSerializable
    data class WeatherMessage(
        val info: InfoMessages = InfoMessages(),
        val help: HelpMessage = HelpMessage()
    ) {
        @ConfigSerializable
        data class HelpMessage(
            val infoSun: MiniMessageComponent = "Использование команды: /sun".asMiniMessageComponent(),
            val infoRain: MiniMessageComponent = "Использование команды: /rain".asMiniMessageComponent(),
            val infoStorm: MiniMessageComponent = "Использование команды: /storm".asMiniMessageComponent()
        )

        @ConfigSerializable
        data class InfoMessages(
            val sun: MiniMessageComponent = "<green>Вы установили солнце в мире: <world> </green>".asMiniMessageComponent(),
            val rain: MiniMessageComponent = "<green>Вы установили дождь в мире: <world> </green>".asMiniMessageComponent(),
            val storm: MiniMessageComponent = "<green>Вы установили шторм в мире: <world> </green>".asMiniMessageComponent(),
        )
    }


    @ConfigSerializable
    data class Errors(
        val noPermission: MiniMessageComponent = "У вас нету разрешения на использование этого".asMiniMessageComponent(),
        val playerNotFound: MiniMessageComponent = "Не удалось найти игрока <player>".asMiniMessageComponent(),
        val invalidUsage: MiniMessageComponent = "Вы не правильно используете команду".asMiniMessageComponent(),
        val invalidNumber: MiniMessageComponent = "Введите правильное число".asMiniMessageComponent(),
        val onlyPlayer: MiniMessageComponent = "Данную команду могут использовать только игроки!".asMiniMessageComponent()
    )

    /*

    ---> placeholders <---

     */

    @ConfigSerializable
    data class Placeholders(
        val enabled: MiniMessageComponent = "Включен".asMiniMessageComponent(),
        val disabled: MiniMessageComponent = "Выключен".asMiniMessageComponent(),
        val gamemodeSurvival: MiniMessageComponent = "выживание".asMiniMessageComponent(),
        val gamemodeCreative: MiniMessageComponent = "креатив".asMiniMessageComponent(),
        val gamemodeSpectator: MiniMessageComponent = "наблюдение".asMiniMessageComponent(),
        val gamemodeAdventure: MiniMessageComponent = "Приключения".asMiniMessageComponent(),
    )
}