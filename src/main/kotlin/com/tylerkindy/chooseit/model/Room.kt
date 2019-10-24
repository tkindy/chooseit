package com.tylerkindy.chooseit.model

import me.liuwj.ktorm.entity.Entity
import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.boolean
import me.liuwj.ktorm.schema.varchar

interface Room : Entity<Room> {
    val id: String
    val name: String
    var flip: Boolean?
}

object Rooms : Table<Room>("rooms") {
    val id by varchar("id").primaryKey().bindTo { it.id }
    val name by varchar("name").bindTo { it.name }
    val flip by boolean("flip").bindTo { it.flip }
}

val Room.view: RoomView
    get() = RoomView(
        id = id,
        name = name,
        state = when (flip) {
            true -> "Heads"
            false -> "Tails"
            null -> "Not yet flipped"
        }
    )

data class RoomView(
    val id: String,
    val name: String,
    val state: String
)
