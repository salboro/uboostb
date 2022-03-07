package ru.di.module

import org.koin.dsl.module
import ru.data.datasource.WorkoutPlanDataSource
import ru.data.datasource.WorkoutPlanDataSourceImpl
import ru.data.repository.WorkoutPlanRepositoryImpl
import ru.domain.repository.WorkoutPlanRepository

val workoutPlanModule = module {
    single<WorkoutPlanDataSource> { WorkoutPlanDataSourceImpl() }
    single<WorkoutPlanRepository> { WorkoutPlanRepositoryImpl(get(), get()) }
}