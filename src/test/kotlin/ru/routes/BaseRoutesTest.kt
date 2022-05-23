package ru.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.testing.*
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger
import ru.routes


interface BaseRoutesTest {

    val koinModules: List<Module>
}

fun <R> BaseRoutesTest.withBaseTestApplication(test: TestApplicationEngine.() -> R) {
    withTestApplication({
        install(CallLogging)

        install(ContentNegotiation) {
            gson()
        }

        install(Koin) {
            SLF4JLogger()
            modules(koinModules)
        }

        val secret = "test"
        val issuer = "test"
        val audience = "test"
        val realm = "test"
        install(Authentication) {
            jwt("auth-jwt") {
                this.realm = realm
                verifier(
                    JWT.require(Algorithm.HMAC256(secret))
                        .withAudience(audience)
                        .withIssuer(issuer)
                        .build()
                )

                validate { credential ->
                    val email = credential.payload.getClaim("email").asString()
                    val id = credential.payload.getClaim("id").asString()
                    if (email.isNotBlank() && id.isNotBlank()) {
                        JWTPrincipal(credential.payload)
                    } else {
                        null
                    }
                }
            }
        }

        routes()
    }) {
        test()
    }
}
