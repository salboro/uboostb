package ru.domain.entity.workoutPlan

data class StrengthExerciseInWorkoutPlan(
    val id: Int,
    val title: String,
    val description: String,
    val imageUri: String,
    val sets: List<StrengthExerciseSet>,
)
