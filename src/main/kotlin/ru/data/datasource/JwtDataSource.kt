package ru.data.datasource

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ru.util.envConfig
import java.util.*
import java.util.concurrent.TimeUnit


interface JwtDataSource {

    fun generateToken(userEmail: String): String
}

class JwtDataSourceImpl : JwtDataSource {

    private companion object {

        const val EXPIRED_HOURS = 12L
        const val EMAIL = "email"
    }

    override fun generateToken(userEmail: String): String {
        val expiresTime = Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(EXPIRED_HOURS))
        val secret = envConfig.property("jwt.secret").getString()
        val issuer = envConfig.property("jwt.issuer").getString()
        val audience = envConfig.property("jwt.audience").getString()

        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(EMAIL, userEmail)
            .withExpiresAt(expiresTime)
            .sign(Algorithm.HMAC256(secret))
    }
}