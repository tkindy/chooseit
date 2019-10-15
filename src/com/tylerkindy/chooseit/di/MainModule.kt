package com.tylerkindy.chooseit.di

import com.tylerkindy.chooseit.RoomRoute
import com.tylerkindy.chooseit.getRoom
import com.tylerkindy.chooseit.model.Rooms
import dagger.Module
import dagger.Provides
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.liuwj.ktorm.dsl.insert
import java.nio.ByteBuffer
import java.util.*
import kotlin.random.Random

@Module
class MainModule {
    @KtorExperimentalLocationsAPI
    @Provides
    fun provideServer(): ApplicationEngine {
        return embeddedServer(Netty, port = System.getProperty("server.port").toInt()) {
            install(ContentNegotiation) {
                gson { }
            }
            install(CallLogging)
            install(Locations)
            install(CORS) {
                anyHost()
            }

            routing {
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

                get<RoomRoute.Status> { params ->
                    val room = getRoom(params.room.id)
                    val value = when (room.flip) {
                        true -> "Heads"
                        false -> "Tails"
                        null -> "Not yet flipped"
                    }

                    call.respond(value)
                }

                post<RoomRoute.Flip> { params ->
                    val room = getRoom(params.room.id)

                    room.flip = Random.nextBoolean()
                    room.flushChanges()
                    call.respond("OK")
                }
            }
        }
    }
}
