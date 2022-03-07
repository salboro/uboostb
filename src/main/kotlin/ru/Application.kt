package ru

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
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
import ru.di.module.workoutPlanModule
import ru.util.envConfig
import ru.util.provideConfig
import java.net.URI

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    provideConfig()

    install(CallLogging)

    install(ContentNegotiation) {
        gson()
    }

    install(Koin) {
        SLF4JLogger()
        modules(healthCheckModule, authenticationModule, workoutPlanModule)
    }

    val secret = envConfig.property("jwt.secret").getString()
    val issuer = envConfig.property("jwt.issuer").getString()
    val audience = envConfig.property("jwt.audience").getString()
    val realm = envConfig.property("jwt.realm").getString()
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
                if (credential.payload.getClaim("email").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }

    val databaseUri = URI(envConfig.property("database.url").getString())
    val databaseDriver = envConfig.property("database.driver").getString()
    val databaseUser = databaseUri.userInfo.split(":")[0]
    val databasePassword = databaseUri.userInfo.split(":")[1]
    val databaseUrl = buildDatabaseUrl(databaseUri)
    Database.connect(
        url = databaseUrl,
        driver = databaseDriver,
        user = databaseUser,
        password = databasePassword
    )

    routes()
}

private fun buildDatabaseUrl(databaseUri: URI): String =
    "jdbc:postgresql://" + databaseUri.host + ':' + databaseUri.port + databaseUri.path + "?sslmode=require"
