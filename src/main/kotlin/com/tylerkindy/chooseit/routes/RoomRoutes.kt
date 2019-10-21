package com.tylerkindy.chooseit.routes

import com.tylerkindy.chooseit.data.RoomManager
import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import javax.inject.Inject
import kotlin.random.Random

@KtorExperimentalLocationsAPI
class RoomRoutes @Inject constructor(
    private val roomManager: RoomManager
) {
    val buildRoomRoute: Route.() -> Unit = {
        post("/new") {
            val id = roomManager.makeNewRoom()
            call.respond(id)
        }

        get<RoomRoute.Status> { params ->
            val room = roomManager.getRoom(params.room.id)
            val value = when (room.flip) {
                true -> "Heads"
                false -> "Tails"
                null -> "Not yet flipped"
            }

            call.respond(value)
        }

        post<RoomRoute.Flip> { params ->
            val room = roomManager.getRoom(params.room.id)

            room.flip = Random.nextBoolean()
            room.flushChanges()
            call.respond("OK")
        }
    }
}

@KtorExperimentalLocationsAPI
@Location("{id}")
data class RoomRoute(val id: String) {
    @Location("/")
    data class Status(val room: RoomRoute)

    @Location("/flip")
    data class Flip(val room: RoomRoute)
}
