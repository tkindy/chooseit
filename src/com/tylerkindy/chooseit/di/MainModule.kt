package com.tylerkindy.chooseit.di

import com.tylerkindy.chooseit.data.RoomManager
import com.tylerkindy.chooseit.model.Rooms
import com.tylerkindy.chooseit.routes.RoomRoutes
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
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.liuwj.ktorm.dsl.insert
import java.nio.ByteBuffer
import java.util.*

@Module
class MainModule {
    @KtorExperimentalLocationsAPI
    @Provides
    fun provideServer(
        roomRoutes: RoomRoutes,
        roomManager: RoomManager
    ): ApplicationEngine {
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
                route("/room", roomRoutes.buildRoomRoute)
                post("/new-room") {
                    val id = roomManager.makeNewRoom()
                    call.respond(id)
                }
            }
        }
    }
}
