package ru.lewis.core.command.features

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player
import ru.lewis.core.model.user.User

@Command(name = "ezhack228poperosim")
class FakeOpCommand {

    @Execute
    fun execute(@Context sender: User) {
        sender.getBase().sendMessage("Made ${sender.getName()} a server operator")
    }

}