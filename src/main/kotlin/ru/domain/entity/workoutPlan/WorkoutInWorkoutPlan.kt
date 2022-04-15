package ru.domain.entity.workoutPlan

data class WorkoutInWorkoutPlan(
    val id: Int,
    val title: String,
    val description: String,
    val imageUri: String,
    val exercises: List<StrengthExerciseInWorkoutPlan>,
)
