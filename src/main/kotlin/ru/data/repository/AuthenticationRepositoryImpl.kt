package ru.data.repository

import ru.data.datasource.JwtDataSource
import ru.data.datasource.UserDataSource
import ru.domain.entity.authentication.User
import ru.domain.entity.authentication.UserCredentials
import ru.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val jwtDataSource: JwtDataSource,
) : AuthenticationRepository {

    override fun register(user: User) {
        userDataSource.add(user.name, user.password, user.email)
    }

    override fun login(user: UserCredentials): String {
        val userId = userDataSource.getId(user.email, user.password)
        return jwtDataSource.generateToken(user.email, userId)
    }

}