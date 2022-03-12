package ru.data.dao

import org.jetbrains.exposed.sql.Table

object UserDao : Table(name = "application_user") {

    val id = integer("id").autoIncrement().uniqueIndex()
    val name = varchar("user_name", length = 15)
    val password = varchar("user_password", length = 80)
    val email = varchar("user_email", length = 55).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}