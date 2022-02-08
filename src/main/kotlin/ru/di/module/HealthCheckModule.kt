package ru.di.module

import org.koin.dsl.module
import ru.data.datasource.HealthCheckDataSource
import ru.data.datasource.HealthCheckDataSourceImpl
import ru.data.repository.HealthCheckRepositoryImpl
import ru.domain.repository.HealthCheckRepository

val healthCheckModule = module {
    single<HealthCheckDataSource> { HealthCheckDataSourceImpl() }
    single<HealthCheckRepository> { HealthCheckRepositoryImpl(get()) }
}