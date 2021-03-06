package com.tylerkindy.chooseit.model

import me.liuwj.ktorm.entity.Entity
import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.boolean
import me.liuwj.ktorm.schema.varchar

interface Room : Entity<Room> {
    val id: String
    val name: String
    var flip: Boolean?
    val singleFlip: Boolean
}

object Rooms : Table<Room>("rooms") {
    val id by varchar("id").primaryKey().bindTo { it.id }
    val name by varchar("name").bindTo { it.name }
    val flip by boolean("flip").bindTo { it.flip }
    val singleFlip by boolean("singleFlip").bindTo { it.singleFlip }
}

val Room.view: RoomView
    get() = RoomView(
        id = id,
        name = name,
        state = when (flip) {
            true -> "Heads"
            false -> "Tails"
            null -> "Not yet flipped"
        },
        canFlip = canFlip
    )

val Room.canFlip: Boolean
    get() = !singleFlip || flip == null

data class RoomView(
    val id: String,
    val name: String,
    val state: String,
    val canFlip: Boolean
)
