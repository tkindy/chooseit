package com.tylerkindy.chooseit

import com.tylerkindy.chooseit.di.DaggerAppComponent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.engine.ApplicationEngine
import me.liuwj.ktorm.database.Database

@KtorExperimentalLocationsAPI
fun main() {
    Database.connect(
        System.getenv("JDBC_DATABASE_URL"),
        driver = "org.postgresql.Driver"
    )

    val server: ApplicationEngine = DaggerAppComponent.create().server()
    server.start(wait = true)
}
