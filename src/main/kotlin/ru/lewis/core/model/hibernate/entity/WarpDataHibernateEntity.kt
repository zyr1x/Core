package ru.lewis.core.model.hibernate.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "core_warps")
class WarpDataHibernateEntity() {

    @Id
    @Column(name = "warp", nullable = false)
    open var warp: String? = null

    @Column(name = "owner", nullable = false)
    open var owner: UUID? = null

    @Column(name = "xCord", nullable = false)
    open var xCord: Double? = null

    @Column(name = "yCord", nullable = false)
    open var yCord: Double? = null

    @Column(name = "zCord", nullable = false)
    open var zCord: Double? = null

    @Column(name = "yaw", nullable = false)
    open var yaw: Float? = null

    @Column(name = "pitch", nullable = false)
    open var pitch: Float? = null

    @Column(name = "world", nullable = false)
    open var world: String? = null

    constructor(
        owner: UUID,
        warp: String,
        xCord: Double,
        yCord: Double,
        zCord: Double,
        yaw: Float,
        pitch: Float,
        world: String
    ): this() {
        this.owner = owner
        this.warp = warp
        this.xCord = xCord
        this.yCord = yCord
        this.zCord = zCord
        this.yaw = yaw
        this.pitch = pitch
        this.world = world
    }

}