package ru.routes.authentication

import io.ktor.application.*
import io.ktor.routing.*

fun Application.registerAuthenticationRoutes() {
    routing {
        registerUser()
        loginUser()
    }
}