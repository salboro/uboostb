package ru.data.entity

data class StrengthExerciseSetModel(
    val workoutId: Int,
    val exerciseId: Int,
    val index: Int,
    val weight: Float,
    val repetitionsNumber: Int,
)