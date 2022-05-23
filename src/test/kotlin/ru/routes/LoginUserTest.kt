package ru.routes

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import org.koin.dsl.module
import ru.domain.entity.authentication.UserCredentials
import ru.domain.repository.AuthenticationRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginUserTest : BaseRoutesTest {

    private val authenticationRepository = mockk<AuthenticationRepository>()

    private val module = module {
        single { authenticationRepository }
    }

    override val koinModules = listOf(module)

    private val gson = Gson()

    private val userCredentials = UserCredentials("test", "test")
    private val jwtToken = "jwt token"
    private val requestBody = gson.toJson(userCredentials)
    private val responseBody = gson.toJson(mapOf("token" to jwtToken))

    @Test
    fun `route login with valid credentials EXPECT response token`() = withBaseTestApplication {
        with(handleRequest(HttpMethod.Post, "/login") {
            every { authenticationRepository.login(userCredentials) } returns jwtToken

            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(requestBody)
        }) {
            val actual = response.content
            val expected = responseBody

            assertEquals(expected, actual)
        }
    }
}