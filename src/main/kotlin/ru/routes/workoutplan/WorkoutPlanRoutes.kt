package ru.routes.workoutplan

import io.ktor.application.*
import io.ktor.routing.*

fun Application.registerWorkoutPlanRoutes() {
    routing {
        getUserWorkoutPlans()
    }
}