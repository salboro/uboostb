package ru.routes.feed

import io.ktor.application.*
import io.ktor.routing.*

fun Application.registerFeedRoutes() {
    routing {
        getFeed()
    }
}