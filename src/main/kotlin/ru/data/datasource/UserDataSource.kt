package ru.data.datasource

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.data.crypto.BCryptoUtil
import ru.data.dao.UserDao

interface UserDataSource {

    fun add(name: String, password: String, email: String): Int

    fun getId(email: String, password: String): Int

    fun getId(email: String): Int
}

class UserDataSourceImpl(private val bCryptoUtil: BCryptoUtil) : UserDataSource {

    override fun add(name: String, password: String, email: String): Int = transaction {
        val userHashPassword = bCryptoUtil.hash(password)
        UserDao.insert {
            it[UserDao.name] = name
            it[UserDao.password] = userHashPassword
            it[UserDao.email] = email
        } get UserDao.id
    }

    override fun getId(email: String, password: String): Int = transaction {
        val query = UserDao.select { UserDao.email eq email}.single()
        val (userId, hashPassword) = Pair(query[UserDao.id], query[UserDao.password])
        if (bCryptoUtil.verify(password, hashPassword)) {
            userId
        } else {
            throw NoSuchElementException("Wrong password")
        }
    }

    override fun getId(email: String): Int = transaction {
        UserDao.select { UserDao.email eq email }.single()[UserDao.id]
    }
}

