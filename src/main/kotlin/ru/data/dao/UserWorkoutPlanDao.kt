package ru.data.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.date

object UserWorkoutPlanDao : Table("users_workout_plan") {

    val workoutPlanId = (integer("workout_plan_id") references WorkoutPlanDao.id)
    val userId = (integer("user_id") references UserDao.id)
    val chooseDate = date("choose_date")

    override val primaryKey = PrimaryKey(workoutPlanId, userId, chooseDate)
}