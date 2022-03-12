package ru.data.datasource

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ru.util.envConfig
import java.util.*
import java.util.concurrent.TimeUnit


interface JwtDataSource {

    fun generateToken(userEmail: String, userId: Int): String
}

class JwtDataSourceImpl : JwtDataSource {

    private companion object {

        const val EXPIRED_HOURS = 12L
        const val EMAIL = "email"
        const val ID = "id"
    }

    private val expiresTime = Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(EXPIRED_HOURS))
    private val secret = envConfig.property("jwt.secret").getString()
    private val issuer = envConfig.property("jwt.issuer").getString()
    private val audience = envConfig.property("jwt.audience").getString()
    private val algorithm = Algorithm.HMAC256(secret)

    override fun generateToken(userEmail: String, userId: Int): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(EMAIL, userEmail)
            .withClaim(ID, userId)
            .withExpiresAt(expiresTime)
            .sign(algorithm)
    }
}