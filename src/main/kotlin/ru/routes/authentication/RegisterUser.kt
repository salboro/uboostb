package ru.routes.authentication

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.postgresql.util.PSQLException
import ru.domain.entity.authentication.User
import ru.domain.repository.AuthenticationRepository
import ru.util.error.DATABASE_UNAVAILABLE_MESSAGE
import ru.util.error.ILLEGAL_ARGUMENT_MESSAGE
import ru.util.error.INTERNAL_SERVER_ERROR_MESSAGE
import ru.util.error.SUCCESS_REGISTRATION_MESSAGE

fun Route.registerUser() {
    val authenticationRepository: AuthenticationRepository by inject()

    route("/register") {
        post {
            try {
                val user = call.receive<User>()
                authenticationRepository.register(user)
                call.respondText(SUCCESS_REGISTRATION_MESSAGE, status = HttpStatusCode.Created)
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