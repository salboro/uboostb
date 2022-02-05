package ru

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger
import ru.di.module.healthCheckModule

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(ContentNegotiation) {
        gson()
    }

    install(Koin) {
        SLF4JLogger()
        modules(healthCheckModule)
    }

    routes()
}