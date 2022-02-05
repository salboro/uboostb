package ru.routes.healthcheck

import io.ktor.application.*
import io.ktor.routing.*

fun Application.registerHealthCheckRoutes() {
    routing {
        healthCheckRouting()
    }
}

