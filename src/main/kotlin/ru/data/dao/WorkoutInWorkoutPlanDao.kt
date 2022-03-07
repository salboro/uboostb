package ru.data.dao

import org.jetbrains.exposed.sql.Table

object WorkoutInWorkoutPlanDao : Table("workout_in_workout_plan") {

    val id = integer("id").autoIncrement().uniqueIndex()
    val workoutPlanId = (integer("workout_plan_id") references WorkoutPlanDao.id)
    val name = varchar("name", length = 50)
    val description = text("description").nullable()
    val imageUri = text("image_uri").nullable()

    override val primaryKey = PrimaryKey(id, workoutPlanId)
}