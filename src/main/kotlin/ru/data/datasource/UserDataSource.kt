package ru.data.datasource

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.data.crypto.Crypto
import ru.data.dao.UserDao
import ru.data.dao.UserDao.email
import ru.data.dao.UserDao.password
import ru.domain.entity.authentication.User
import ru.domain.entity.authentication.UserCredentials

interface UserDataSource {

    fun add(user: User): Int

    fun getId(userCredentials: UserCredentials): Int

    fun getId(email: String): Int
}

class UserDataSourceImpl(private val crypto: Crypto) : UserDataSource {

    override fun add(user: User): Int = transaction {
        val userHashPassword = crypto.hash(user.password)
        UserDao.insert {
            it[name] = name
            it[password] = userHashPassword
            it[email] = email
        } get UserDao.id
    }

    override fun getId(userCredentials: UserCredentials): Int = transaction {
        val query = UserDao.select { email eq userCredentials.email}.single()
        val (userId, hashPassword) = query[UserDao.id] to query[password]
        if (crypto.verify(userCredentials.password, hashPassword)) {
            userId
        } else {
            throw NoSuchElementException("Wrong password")
        }
    }

    override fun getId(email: String): Int = transaction {
        UserDao.select { UserDao.email eq email }.single()[UserDao.id]
    }
}

