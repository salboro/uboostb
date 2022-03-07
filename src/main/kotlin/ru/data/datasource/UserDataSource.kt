package ru.data.datasource

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.data.dao.UserDao
import ru.domain.entity.authentication.User
import ru.domain.entity.authentication.UserLogin

interface UserDataSource {

    fun add(user: User): Int

    fun getId(user: UserLogin): Int

    fun getId(email: String): Int
}
class UserDataSourceImpl : UserDataSource {

    override fun add(user: User): Int = transaction {
        UserDao.insert {
            it[name] = user.name
            it[password] = user.password
            it[email] = user.email
        } get UserDao.id
    }

    override fun getId(user: UserLogin): Int = transaction {
        UserDao.select { UserDao.email.eq(user.email) and UserDao.password.eq(user.password) }.single()[UserDao.id]
    }

    override fun getId(email: String): Int = transaction {
        UserDao.select { UserDao.email eq email }.single()[UserDao.id]
    }
}

