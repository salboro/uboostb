package ru.data.dao

import org.jetbrains.exposed.sql.Table

object ExerciseDao : Table("exercise") {

    val id = integer("id").autoIncrement().uniqueIndex()
    val name = varchar("name", length = 50)
    val description = text("description")
    val iconUri = text("icon_uri").nullable()

    override val primaryKey = PrimaryKey(id)
}