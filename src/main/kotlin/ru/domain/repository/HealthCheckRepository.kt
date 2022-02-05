package ru.domain.repository

interface HealthCheckRepository {

    fun get(): String
}