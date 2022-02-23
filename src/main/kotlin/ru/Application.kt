package ru

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger
import ru.di.module.authenticationModule
import ru.di.module.healthCheckModule

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    val conf = ConfigFactory.load()

    install(CallLogging)

    install(ContentNegotiation) {
        gson()
    }

    install(Koin) {
        SLF4JLogger()
        modules(healthCheckModule, authenticationModule)
    }

    val secret = conf.getString("jwt.secret")
    val issuer = conf.getString("jwt.issuer")
    val audience = conf.getString("jwt.audience")
    val myRealm = conf.getString("jwt.realm")
    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT.require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("email").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }

    Database.connect(
        url = conf.getString("database.databaseUrl"),
        driver = conf.getString("database.databaseDriver"),
        user = conf.getString("database.databaseUser"),
        password = conf.getString("database.databasePassword")
    )

    routes()
}