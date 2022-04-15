package ru.domain.entity.exercise

import ru.domain.entity.workoutPlan.StrengthExerciseSet

data class Exercise(
    val id: Int,
    val title: String,
    val imageUri: String,
    val description: String,
    val sets: List<StrengthExerciseSet> = emptyList()
)
