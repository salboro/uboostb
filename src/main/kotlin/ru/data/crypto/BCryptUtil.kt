package ru.data.crypto

import at.favre.lib.crypto.bcrypt.BCrypt
import ru.util.envConfig

interface BCryptoUtil {

    fun hash(password: String): String
    fun verify(password: String, hash: String): Boolean
}

class BCryptoUtilImpl : BCryptoUtil {

    private companion object {
        const val BCRYPT_COST = 6
    }

    private val salt = envConfig.property("crypt.cryptSalt").getString()

    override fun hash(password: String): String =
        BCrypt.withDefaults().hash(
            BCRYPT_COST,
            salt.toByteArray(Charsets.UTF_8),
            password.toByteArray(Charsets.UTF_8)
        ).toString(Charsets.UTF_8)

    override fun verify(password: String, hash: String): Boolean =
        BCrypt.verifyer()
            .verify(password.toByteArray(Charsets.UTF_8), hash.toByteArray(Charsets.UTF_8))
            .verified
}