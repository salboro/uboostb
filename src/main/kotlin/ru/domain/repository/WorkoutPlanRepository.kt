package ru.domain.repository

import ru.domain.entity.workoutPlan.WorkoutPlan

interface WorkoutPlanRepository {

    fun getListByEmail(email: String): List<WorkoutPlan>
    fun getList(): List<WorkoutPlan>
}