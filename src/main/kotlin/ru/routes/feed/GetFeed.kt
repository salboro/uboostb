package ru.routes.feed

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.postgresql.util.PSQLException
import ru.domain.repository.FeedRepository
import ru.util.error.DATABASE_UNAVAILABLE_MESSAGE
import ru.util.error.ILLEGAL_ARGUMENT_MESSAGE
import ru.util.error.INTERNAL_SERVER_ERROR_MESSAGE

fun Route.getFeed() {
    val feedRepository: FeedRepository by inject()

    route("/feed") {
        authenticate("auth-jwt") {
            get {
                try {
                    val count = call.request.queryParameters["count"]?.toInt()
                    val feed = count?.let {
                        feedRepository.get(count)
                    } ?: feedRepository.get()
                    call.respond(feed)
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