package ru.domain.entity.workoutPlan

data class WorkoutPlan(
    val id: Int,
    val name: String,
    val description: String,
    val imageUri: String,
    val workouts: List<WorkoutInWorkoutPlan>,
)
