package ru.di.module

import org.koin.dsl.module
import ru.data.repository.FeedRepositoryImpl
import ru.domain.repository.FeedRepository

val feedModule = module {
    single<FeedRepository> { FeedRepositoryImpl(get(), get(), get(), get()) }
}