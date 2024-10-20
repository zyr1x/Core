package ru.lewis.core.model.hibernate.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "core_cooldowns")
open class PlayerKitCooldownHibernateEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    open var id: Long? = null

    @Column(name = "player", nullable = false)
    open var player: UUID? = null

    @Column(name = "kit", nullable = false, unique = true)
    open var kit: String? = null

    @Column(name = "cooldown", nullable = false, unique = true)
    open var cooldown: Long? = null

    constructor(

        player: UUID,
        kit: String,
        cooldown: Long

    ): this() {

        this.player = player
        this.kit = kit
        this.cooldown = cooldown

    }

}