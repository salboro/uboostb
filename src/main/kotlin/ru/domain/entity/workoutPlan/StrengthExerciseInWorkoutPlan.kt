package ru.domain.entity.workoutPlan

data class StrengthExerciseInWorkoutPlan(
    val id: Int,
    val name: String,
    val description: String,
    val imageUri: String,
    val sets: List<StrengthExerciseSet>,
)
