package ru

import io.ktor.application.*
import ru.routes.authentication.registerAuthenticationRoutes
import ru.routes.healthcheck.registerHealthCheckRoutes
import ru.routes.workoutplan.registerWorkoutPlanRoutes

fun Application.routes() {
    registerHealthCheckRoutes()
    registerAuthenticationRoutes()
    registerWorkoutPlanRoutes()
}