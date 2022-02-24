package ru.domain.repository

import ru.domain.entity.authentication.User
import ru.domain.entity.authentication.UserLogin

interface AuthenticationRepository {

    fun register(user: User)

    fun login(user: UserLogin): String
}