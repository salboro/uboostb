package ru.domain.entity.workoutPlan

data class WorkoutPlan(
    val id: Int,
    val title: String,
    val description: String,
    val imageUri: String,
    val workouts: List<WorkoutInWorkoutPlan>,
)
