package ru

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(ContentNegotiation) {
        gson()
    }

    routes()
}