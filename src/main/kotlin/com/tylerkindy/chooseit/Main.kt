package com.tylerkindy.chooseit

import com.tylerkindy.chooseit.di.DaggerAppComponent
import io.ktor.server.engine.ApplicationEngine
import io.ktor.util.KtorExperimentalAPI
import me.liuwj.ktorm.database.Database

@KtorExperimentalAPI
fun main() {
    Database.connect(
        System.getenv("JDBC_DATABASE_URL"),
        driver = "org.postgresql.Driver"
    )

    val server: ApplicationEngine = DaggerAppComponent.create().server()
    server.start(wait = true)
}
