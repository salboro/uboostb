package ru.data.datasource

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.data.dao.UserDao
import ru.domain.entity.authentication.User
import ru.domain.entity.authentication.UserLogin

interface UserDataSource {

    fun add(user: User): Int

    fun getId(user: UserLogin): Int
}
class UserDataSourceImpl : UserDataSource {

    override fun add(user: User): Int = transaction {
        addLogger(Slf4jSqlDebugLogger)

        UserDao.insert {
            it[name] = user.name
            it[password] = user.password
            it[email] = user.email
        } get UserDao.id
    }

    override fun getId(user: UserLogin): Int = transaction {
        addLogger(Slf4jSqlDebugLogger)

        UserDao.select { UserDao.email.eq(user.email) and UserDao.password.eq(user.password) }.single()[UserDao.id]
    }
}

