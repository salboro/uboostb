package ru

import io.ktor.application.*
import ru.routes.healthcheck.registerHealthCheckRoutes

fun Application.routes() {
    registerHealthCheckRoutes()
}