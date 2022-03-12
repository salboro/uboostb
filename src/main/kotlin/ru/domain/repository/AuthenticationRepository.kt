package ru.domain.repository

import ru.domain.entity.authentication.User
import ru.domain.entity.authentication.UserCredentials

interface AuthenticationRepository {

    fun register(user: User)

    fun login(user: UserCredentials): String
}