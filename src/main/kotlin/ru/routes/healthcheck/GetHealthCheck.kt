package ru.routes.healthcheck

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import ru.domain.repository.HealthCheckRepository

fun Route.healthCheckRouting() {
    val healthCheckRepository: HealthCheckRepository by inject()

    route("/health-check") {
        get {
            call.respond(healthCheckRepository.get())
        }
    }
}