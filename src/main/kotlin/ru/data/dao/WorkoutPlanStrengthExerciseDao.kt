package ru.data.dao

import org.jetbrains.exposed.sql.Table

object WorkoutPlanStrengthExerciseDao: Table("workout_plan_strength_exercise") {

    val workoutId = (integer("workout_id") references WorkoutInWorkoutPlanDao.id)
    val exerciseId = (integer("exercise_id") references ExerciseDao.id)
    val setNumber = integer("set_number")
    val weight = float("weight")
    val repetitionsNumber = integer("repetitions_number")

    override val primaryKey = PrimaryKey(workoutId, exerciseId)
}