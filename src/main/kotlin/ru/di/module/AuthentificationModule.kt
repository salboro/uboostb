package ru.di.module

import org.koin.dsl.module
import ru.data.crypto.BCryptoImpl
import ru.data.crypto.Crypto
import ru.data.datasource.JwtDataSource
import ru.data.datasource.JwtDataSourceImpl
import ru.data.datasource.UserDataSource
import ru.data.datasource.UserDataSourceImpl
import ru.data.repository.AuthenticationRepositoryImpl
import ru.domain.repository.AuthenticationRepository

val authenticationModule = module {
    single<Crypto> { BCryptoImpl() }
    single<UserDataSource> { UserDataSourceImpl(get()) }
    single<JwtDataSource> { JwtDataSourceImpl() }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
}