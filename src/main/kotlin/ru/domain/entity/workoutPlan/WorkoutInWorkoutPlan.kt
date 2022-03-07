package ru.domain.entity.workoutPlan

data class WorkoutInWorkoutPlan(
    val id: Int,
    val name: String,
    val description: String,
    val imageUri: String,
    val exercises: List<StrengthExerciseInWorkoutPlan>,
)
