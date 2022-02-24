package ru.routes.authentication

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.postgresql.util.PSQLException
import ru.domain.entity.authentication.UserLogin
import ru.domain.repository.AuthenticationRepository
import ru.util.error.DATABASE_UNAVAILABLE_MESSAGE
import ru.util.error.INTERNAL_SERVER_ERROR_MESSAGE
import ru.util.error.WRONG_EMAIL_OR_PASSWORD_MESSAGE

private const val TOKEN_KEY = "token"

fun Route.loginUser() {
    val authenticationRepository: AuthenticationRepository by inject()

    route("/login") {
        post {
            try {
                val userLogin = call.receive<UserLogin>()
                val token = authenticationRepository.login(userLogin)

                call.respond(hashMapOf(TOKEN_KEY to token))
            } catch (e: NoSuchElementException) {
                call.respondText(WRONG_EMAIL_OR_PASSWORD_MESSAGE, status = HttpStatusCode.NotFound)
            } catch (e: PSQLException) {
                call.respondText(DATABASE_UNAVAILABLE_MESSAGE, status = HttpStatusCode.InternalServerError)
            } catch (e: Throwable) {
                call.respondText(INTERNAL_SERVER_ERROR_MESSAGE, status = HttpStatusCode.InternalServerError)
            }
        }
    }
}