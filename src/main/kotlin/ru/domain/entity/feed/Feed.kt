package ru.domain.entity.feed

import ru.domain.entity.exercise.Exercise
import ru.domain.entity.workoutPlan.WorkoutPlan

data class Feed(
    val workoutPlans: List<WorkoutPlan>,
    val exercises: List<Exercise>,
    val news: List<News>
)
