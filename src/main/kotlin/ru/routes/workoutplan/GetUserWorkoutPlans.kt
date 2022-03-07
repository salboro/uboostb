package ru.routes.workoutplan

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.postgresql.util.PSQLException
import ru.domain.repository.WorkoutPlanRepository
import ru.util.error.DATABASE_UNAVAILABLE_MESSAGE
import ru.util.error.ILLEGAL_ARGUMENT_MESSAGE
import ru.util.error.INTERNAL_SERVER_ERROR_MESSAGE

fun Route.getUserWorkoutPlans() {
    val workoutPlanRepository: WorkoutPlanRepository by inject()

    route("/user/workoutplans") {
        authenticate("auth-jwt") {
            get {
                try {
                    val principal = call.principal<JWTPrincipal>()
                    val email = principal!!.payload.getClaim("email").asString()
                    val workoutPlans = workoutPlanRepository.getListByEmail(email)
                    call.respond(workoutPlans)
                } catch (e: PSQLException) {
                    call.respondText(DATABASE_UNAVAILABLE_MESSAGE + e.message, status = HttpStatusCode.InternalServerError)
                } catch (e: IllegalArgumentException) {
                    call.respondText(ILLEGAL_ARGUMENT_MESSAGE + e.message, status = HttpStatusCode.BadRequest)
                } catch (e: Throwable) {
                    call.respondText(INTERNAL_SERVER_ERROR_MESSAGE + e.message, status = HttpStatusCode.InternalServerError)
                }
            }
        }

    }
}