package ru.data.entity

data class WorkoutInWorkoutPlanModel(
    val id: Int,
    val workoutPlanId: Int,
    val name: String,
    val description: String?,
    val imageUri: String?,
)
