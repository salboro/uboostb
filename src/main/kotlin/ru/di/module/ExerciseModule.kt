package ru.di.module

import org.koin.dsl.module
import ru.data.datasource.ExerciseDataSource
import ru.data.datasource.ExerciseDataSourceImpl

val exerciseModule = module {

    single<ExerciseDataSource> { ExerciseDataSourceImpl() }
}