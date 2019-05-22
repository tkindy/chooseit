package com.tylerkindy.chooseit

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location

@KtorExperimentalLocationsAPI
@Location("/room/{id}")
data class RoomRoute(val id: String) {
    @Location("/status")
    data class Status(val room: RoomRoute)

    @Location("/flip")
    data class Flip(val room: RoomRoute)
}
