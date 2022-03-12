package ru.data.repository

import ru.data.datasource.UserDataSource
import ru.data.datasource.WorkoutPlanDataSource
import ru.domain.entity.workoutPlan.WorkoutPlan
import ru.domain.repository.WorkoutPlanRepository

class WorkoutPlanRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val workoutPlanDataSource: WorkoutPlanDataSource
): WorkoutPlanRepository {

    override fun getListByEmail(email: String): List<WorkoutPlan> {
        val userId = userDataSource.getId(email)

        return workoutPlanDataSource.getListByUserId(userId)
    }

    override fun getList(): List<WorkoutPlan> =
        workoutPlanDataSource.getList()
}