package com.tylerkindy.chooseit.data

import com.tylerkindy.chooseit.model.Room
import com.tylerkindy.chooseit.model.Rooms
import io.ktor.features.NotFoundException
import io.ktor.util.KtorExperimentalAPI
import me.liuwj.ktorm.dsl.insert
import me.liuwj.ktorm.entity.findById
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject

private const val MAX_NAME_LENGTH = 63;

@KtorExperimentalAPI
class RoomManager @Inject constructor() {
    fun makeNewRoom(name: String, singleFlip: Boolean): String {
        if (name.length > MAX_NAME_LENGTH) throw NameTooLongException()

        val uuid = UUID.randomUUID()
        val bytes = ByteBuffer.wrap(Array<Byte>(16) { 0 }.toByteArray())
            .putLong(uuid.mostSignificantBits)
            .putLong(uuid.leastSignificantBits)
            .array()
        val id = Base64.getUrlEncoder().encodeToString(bytes)!!

        Rooms.insert {
            it.id to id
            it.name to name
            it.singleFlip to singleFlip
        }

        return id
    }

    fun getRoom(id: String): Room {
        return Rooms.findById(id) ?: throw NotFoundException("Invalid room ID $id")
    }
}

class NameTooLongException : IllegalArgumentException()
