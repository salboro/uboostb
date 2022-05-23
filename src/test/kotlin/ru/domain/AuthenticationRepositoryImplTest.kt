package ru.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import ru.data.datasource.JwtDataSource
import ru.data.datasource.UserDataSource
import ru.data.repository.AuthenticationRepositoryImpl
import ru.domain.entity.authentication.User
import ru.domain.entity.authentication.UserCredentials
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthenticationRepositoryImplTest {

    private val userDataSource = mockk<UserDataSource>()
    private val jwtDataSource = mockk<JwtDataSource>()
    private val authenticationRepositoryImpl = AuthenticationRepositoryImpl(userDataSource, jwtDataSource)

    @Test
    fun `register EXPECT add user with data source`() {
        val user = User("test", "test", "test")
        val userId = 1

        every { userDataSource.add(user) } returns userId

        authenticationRepositoryImpl.register(user)

        verify { userDataSource.add(user) }
    }

    @Test
    fun `login EXPECT jwt token`() {
        val userCredentials = UserCredentials("test", "test")
        val userId = 1
        val expected = "jwt token"

        every { userDataSource.getId(userCredentials) } returns userId
        every { jwtDataSource.generateToken(userCredentials.email, userId) } returns expected

        val actual = authenticationRepositoryImpl.login(userCredentials)

        assertEquals(expected, actual)
    }
}