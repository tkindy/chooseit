package com.tylerkindy.chooseit

import com.tylerkindy.chooseit.di.DaggerAppComponent
import com.tylerkindy.chooseit.model.Room
import com.tylerkindy.chooseit.model.Rooms
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
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.insert
import me.liuwj.ktorm.dsl.select
import me.liuwj.ktorm.entity.findById
import java.nio.ByteBuffer
import java.util.*
import kotlin.random.Random

@KtorExperimentalLocationsAPI
fun main() {
    Database.connect(
        System.getenv("JDBC_DATABASE_URL"),
        driver = "org.postgresql.Driver"
    )

    val server: ApplicationEngine = DaggerAppComponent.create().server()
    server.start(wait = true)
}

fun getRoom(id: String): Room = Rooms.findById(id) ?: throw IllegalArgumentException("Invalid ID: $id")
