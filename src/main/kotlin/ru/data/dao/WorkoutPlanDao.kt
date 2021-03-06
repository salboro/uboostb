package ru.data.dao

import org.jetbrains.exposed.sql.Table

object WorkoutPlanDao : Table("workout_plan") {

    val id = integer("id").autoIncrement().uniqueIndex()
    val name = varchar("name", length = 50)
    val description = text("description")
    val imageUri = text("image_uri").nullable()

    override val primaryKey = PrimaryKey(id)
}