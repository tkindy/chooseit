package com.tylerkindy.chooseit.model

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.util.*

data class Snippet(val text: String)

val snippets = Collections.synchronizedList(
    mutableListOf(
        Snippet("hello"),
        Snippet("world")
    )
)

data class PostSnippet(val snippet: PostSnippet.Text) {
    data class Text(val text: String)
}

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            gson { }
        }
        routing {
            get("/") {
                call.respondText("Hello, world!", ContentType.Text.Plain)
            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }

            route("/snippets") {
                get {
                    call.respond(mapOf("snippets" to synchronized(snippets) { snippets.toList() }))
                }
                post {
                    val post = call.receive<PostSnippet>()
                    snippets += Snippet(post.snippet.text)
                    call.respond(mapOf("OK" to true))
                }
            }
        }
    }

    server.start(wait = true)
}
