package ru

import io.ktor.application.*
import ru.routes.authentication.registerAuthenticationRoutes
import ru.routes.healthcheck.registerHealthCheckRoutes

fun Application.routes() {
    registerHealthCheckRoutes()
    registerAuthenticationRoutes()
}