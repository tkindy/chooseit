package com.tylerkindy.chooseit

import com.tylerkindy.chooseit.model.Rooms
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.insert
import me.liuwj.ktorm.dsl.select
import java.nio.ByteBuffer
import java.util.*

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:32768/chooseit",
        driver = "org.postgresql.Driver",
        user = "chooseit_user",
        password = "password"
    )

    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            gson { }
        }
        install(CallLogging)

        routing {
            get("/rooms") {
                val roomIds = Rooms.select(Rooms.id).map { it[Rooms.id]!! }

                call.respond(
                    mapOf(
                        "rooms" to roomIds
                    )
                )
            }

            post("/new-room") {
                val uuid = UUID.randomUUID()
                val bb = ByteBuffer.wrap(Array<Byte>(16) { 0 }.toByteArray())
                bb.putLong(uuid.mostSignificantBits)
                bb.putLong(uuid.leastSignificantBits)
                val id = Base64.getUrlEncoder().encodeToString(bb.array())!!

                Rooms.insert {
                    it.id to id
                }

                call.respond(id)
            }
        }
    }

    server.start(wait = true)
}
