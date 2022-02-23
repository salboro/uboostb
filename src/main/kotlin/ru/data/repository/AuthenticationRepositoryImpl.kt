package ru.data.repository

import ru.data.datasource.JwtDataSource
import ru.data.datasource.UserDataSource
import ru.domain.entity.authentication.User
import ru.domain.entity.authentication.UserLogin
import ru.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val jwtDataSource: JwtDataSource
) : AuthenticationRepository {

    override fun register(user: User) {
        userDataSource.add(user)
    }

    override fun login(user: UserLogin): String =
        jwtDataSource.generateToken(user.email)
}