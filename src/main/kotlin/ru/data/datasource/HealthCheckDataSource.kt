package ru.data.datasource

interface HealthCheckDataSource {

    fun get(): String
}

class HealthCheckDataSourceImpl : HealthCheckDataSource {

    private companion object {
        const val HEALTH_CHECK_MESSAGE = "Alive..."
    }

    override fun get(): String = HEALTH_CHECK_MESSAGE
}