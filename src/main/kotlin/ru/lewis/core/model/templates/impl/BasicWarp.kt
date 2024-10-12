package ru.lewis.core.model.templates.impl

import com.google.inject.assistedinject.Assisted
import jakarta.inject.Inject
import org.bukkit.Location
import ru.lewis.core.model.templates.Warp
import java.util.*

class BasicWarp @Inject constructor(

    @Assisted private val name: String,
    @Assisted private val owner: UUID,
    @Assisted private val location: Location

) : Warp {

    override fun getName(): String = name

    override fun getOwner(): UUID = owner

    override fun getLocation(): Location = location

}