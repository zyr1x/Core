package ru.lewis.core.model.templates.impl

import com.google.inject.assistedinject.Assisted
import jakarta.inject.Inject
import org.bukkit.Location
import ru.lewis.core.model.templates.Home

class BasicHome @Inject constructor(
    @Assisted private val name: String,
    @Assisted private val location: Location
) : Home {

    override fun getName(): String = name

    override fun getLocation(): Location = location


}