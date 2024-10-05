package ru.lewis.core.model.hibernate.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "core_homes")
open class PlayerDataHomeHibernateEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    open var id: Long? = null

    @Column(name = "owner", nullable = false)
    open var owner: UUID? = null

    @Column(name = "home", nullable = false)
    open var home: String? = null

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
        home: String,
        xCord: Double,
        yCord: Double,
        zCord: Double,
        yaw: Float,
        pitch: Float,
        world: String
    ): this() {
        this.owner = owner
        this.home = home
        this.xCord = xCord
        this.yCord = yCord
        this.zCord = zCord
        this.yaw = yaw
        this.pitch = pitch
        this.world = world
    }

}