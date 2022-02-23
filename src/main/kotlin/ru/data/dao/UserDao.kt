package ru.data.dao

import org.jetbrains.exposed.sql.Table

object UserDao : Table(name = "application_user") {

    val id = integer("id").autoIncrement()
    val name = varchar("user_name", length = 15)
    val password = varchar("user_password", length = 15)
    val email = varchar("user_email", length = 55)

    override val primaryKey = PrimaryKey(id)

}