package ru.util

import io.ktor.application.*
import io.ktor.config.*

lateinit var envConfig: ApplicationConfig
    private set

fun Application.provideConfig() {
    envConfig = environment.config
}