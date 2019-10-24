package com.tylerkindy.chooseit.routes

import com.tylerkindy.chooseit.data.NameTooLongException
import com.tylerkindy.chooseit.data.RoomManager
import com.tylerkindy.chooseit.model.view
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import javax.inject.Inject
import kotlin.random.Random

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class RoomRoutes @Inject constructor(
    private val roomManager: RoomManager
) {
    val buildRoomRoute: Route.() -> Unit = {
        post<NewRoomRoute> { params ->
            try {
                val id = roomManager.makeNewRoom(params.name)
                val room = roomManager.getRoom(id)
                println(room.view)
                call.respond(room.view)
            } catch (e: NameTooLongException) {
                call.respond(HttpStatusCode.BadRequest, "NAME_TOO_LONG")
            }
        }

        get<RoomRoute.Status> { params ->
            val room = roomManager.getRoom(params.room.id)
            call.respond(room.view)
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
@Location("/")
data class NewRoomRoute(val name: String)

@KtorExperimentalLocationsAPI
@Location("{id}")
data class RoomRoute(val id: String) {
    @Location("/")
    data class Status(val room: RoomRoute)

    @Location("/flip")
    data class Flip(val room: RoomRoute)
}
