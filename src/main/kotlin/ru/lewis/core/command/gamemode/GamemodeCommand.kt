package ru.lewis.core.command.gamemode

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.GameMode
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User
import ru.lewis.core.service.ConfigurationService

@Command(name = "gamemode", aliases = ["gm"])
@Permission("core.command.gamemode")
class GamemodeCommand @Inject constructor(
    private val configurationService: ConfigurationService
){
    private val message get() = configurationService.messages.common.gameMode

    @Execute
    fun execute(@Context sender: User,
                @Arg gameMode: GameMode)  {

        sender.getBase().gameMode = gameMode
        sender.getBase().sendMessage(message.info.feedBack.resolve(
            Placeholder.unparsed(
                "gamemode", gameMode.name)
        ))
    }

    @Execute
    @Permission("core.command.gamemode.other")
    fun executeOther(
        @Context sender: User,
        @Arg gameMode: GameMode,
        @Arg target: User
    ) {

        target.getBase().gameMode = gameMode

        sender.getBase().sendMessage(message.info.feedBackToTarget
            .resolve(
                Placeholder.unparsed(
                    "gamemode",
                    gameMode.name
                ),
                Placeholder.unparsed(
                    "player",
                    target.getName()
                )
            )
        )

        target.getBase().sendMessage(message.info.feedBackTarget
            .resolve(
                Placeholder.unparsed(
                    "gamemode",
                    gameMode.name
                ),
                Placeholder.unparsed(
                    "player",
                    sender.getName()
                )
            )
        )


    }


}