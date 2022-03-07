package ru.data.entity

data class StrengthExerciseInWorkoutPlanModel(
    val id: Int,
    val workoutId: Int,
    val name: String,
    val description: String,
    val imageUri: String?,
)