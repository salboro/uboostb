package ru.data.repository

import ru.data.datasource.HealthCheckDataSource
import ru.domain.repository.HealthCheckRepository

class HealthCheckRepositoryImpl(private val dataSource: HealthCheckDataSource) : HealthCheckRepository {

    override fun get(): String = dataSource.get()
}