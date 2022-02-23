package ru.di.module

import org.koin.dsl.module
import ru.data.datasource.JwtDataSource
import ru.data.datasource.JwtDataSourceImpl
import ru.data.datasource.UserDataSource
import ru.data.datasource.UserDataSourceImpl
import ru.data.repository.AuthenticationRepositoryImpl
import ru.domain.repository.AuthenticationRepository

val authenticationModule = module {
    single<UserDataSource> { UserDataSourceImpl() }
    single<JwtDataSource> { JwtDataSourceImpl() }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
}