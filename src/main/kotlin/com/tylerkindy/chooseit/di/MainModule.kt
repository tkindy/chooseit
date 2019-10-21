package com.tylerkindy.chooseit.di

import com.tylerkindy.chooseit.routes.RoomRoutes
import dagger.Module
import dagger.Provides
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

@Module
class MainModule {
    @KtorExperimentalLocationsAPI
    @Provides
    fun provideServer(
        roomRoutes: RoomRoutes
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
                route("/rooms", roomRoutes.buildRoomRoute)
            }
        }
    }
}
