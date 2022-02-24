package ru.data.datasource

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ru.util.jwt.JWT_AUDIENCE
import ru.util.jwt.JWT_ISSUER
import ru.util.jwt.JWT_SECRET
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

        return JWT.create()
            .withAudience(JWT_AUDIENCE)
            .withIssuer(JWT_ISSUER)
            .withClaim(EMAIL, userEmail)
            .withExpiresAt(expiresTime)
            .sign(Algorithm.HMAC256(JWT_SECRET))
    }
}