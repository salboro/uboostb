package ru.data.datasource

import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.data.dao.*
import ru.data.entity.StrengthExerciseInWorkoutPlanModel
import ru.data.entity.StrengthExerciseSetModel
import ru.data.entity.WorkoutInWorkoutPlanModel
import ru.data.entity.WorkoutPlanModel
import ru.domain.entity.workoutPlan.StrengthExerciseInWorkoutPlan
import ru.domain.entity.workoutPlan.StrengthExerciseSet
import ru.domain.entity.workoutPlan.WorkoutInWorkoutPlan
import ru.domain.entity.workoutPlan.WorkoutPlan

interface WorkoutPlanDataSource {

    fun getListByUserId(userId: Int): List<WorkoutPlan>
    fun getList(): List<WorkoutPlan>
}

class WorkoutPlanDataSourceImpl : WorkoutPlanDataSource {

    override fun getListByUserId(userId: Int): List<WorkoutPlan> = transaction {
        val query = getQueryWorkoutPlansByUserId(userId)
        getWorkoutPlansFromQuery(query)
    }

    override fun getList(): List<WorkoutPlan> = transaction {
        val query = getQueryWorkoutPlans()
        getWorkoutPlansFromQuery(query)
    }

    private fun getQueryWorkoutPlansByUserId(userId: Int) =
        WorkoutPlanDao
            .join(
                UserWorkoutPlanDao,
                JoinType.INNER,
                additionalConstraint = { UserWorkoutPlanDao.userId eq userId }
            )
            .join(
                WorkoutInWorkoutPlanDao,
                JoinType.INNER,
                additionalConstraint = { WorkoutInWorkoutPlanDao.workoutPlanId eq WorkoutPlanDao.id }
            )
            .join(
                WorkoutPlanStrengthExerciseDao,
                JoinType.INNER,
                additionalConstraint = { WorkoutPlanStrengthExerciseDao.workoutId eq WorkoutInWorkoutPlanDao.id }
            )
            .join(
                ExerciseDao,
                JoinType.INNER,
                additionalConstraint = { ExerciseDao.id eq WorkoutPlanStrengthExerciseDao.exerciseId }
            )
            .slice(
                WorkoutPlanDao.id,
                WorkoutPlanDao.name,
                WorkoutPlanDao.description,
                WorkoutPlanDao.imageUri,
                WorkoutInWorkoutPlanDao.id,
                WorkoutInWorkoutPlanDao.name,
                WorkoutInWorkoutPlanDao.description,
                WorkoutInWorkoutPlanDao.imageUri,
                WorkoutPlanStrengthExerciseDao.exerciseId,
                WorkoutPlanStrengthExerciseDao.setNumber,
                WorkoutPlanStrengthExerciseDao.weight,
                WorkoutPlanStrengthExerciseDao.repetitionsNumber,
                ExerciseDao.name,
                ExerciseDao.description,
                ExerciseDao.iconUri,
            )
            .selectAll()

    private fun getQueryWorkoutPlans() =
        WorkoutPlanDao
            .join(
                WorkoutInWorkoutPlanDao,
                JoinType.INNER,
                additionalConstraint = { WorkoutInWorkoutPlanDao.workoutPlanId eq WorkoutPlanDao.id }
            )
            .join(
                WorkoutPlanStrengthExerciseDao,
                JoinType.INNER,
                additionalConstraint = { WorkoutPlanStrengthExerciseDao.workoutId eq WorkoutInWorkoutPlanDao.id }
            )
            .join(
                ExerciseDao,
                JoinType.INNER,
                additionalConstraint = { ExerciseDao.id eq WorkoutPlanStrengthExerciseDao.exerciseId }
            )
            .slice(
                WorkoutPlanDao.id,
                WorkoutPlanDao.name,
                WorkoutPlanDao.description,
                WorkoutPlanDao.imageUri,
                WorkoutInWorkoutPlanDao.id,
                WorkoutInWorkoutPlanDao.name,
                WorkoutInWorkoutPlanDao.description,
                WorkoutInWorkoutPlanDao.imageUri,
                WorkoutPlanStrengthExerciseDao.exerciseId,
                WorkoutPlanStrengthExerciseDao.setNumber,
                WorkoutPlanStrengthExerciseDao.weight,
                WorkoutPlanStrengthExerciseDao.repetitionsNumber,
                ExerciseDao.name,
                ExerciseDao.description,
                ExerciseDao.iconUri,
            ).selectAll()

    private fun getWorkoutPlansFromQuery(query: Query): List<WorkoutPlan> {

        var workoutPlanModels = mutableListOf<WorkoutPlanModel>()
        var workoutModels = mutableListOf<WorkoutInWorkoutPlanModel>()
        var exerciseModels = mutableListOf<StrengthExerciseInWorkoutPlanModel>()
        var setModels = mutableListOf<StrengthExerciseSetModel>()

        query.forEach { row ->
            workoutPlanModels += WorkoutPlanModel(
                row[WorkoutPlanDao.id],
                row[WorkoutPlanDao.name],
                row[WorkoutPlanDao.description],
                row[WorkoutPlanDao.imageUri] ?: "",
            )

            workoutModels += WorkoutInWorkoutPlanModel(
                row[WorkoutInWorkoutPlanDao.id],
                row[WorkoutPlanDao.id],
                row[WorkoutInWorkoutPlanDao.name],
                row[WorkoutInWorkoutPlanDao.description],
                row[WorkoutInWorkoutPlanDao.imageUri],
            )

            exerciseModels += StrengthExerciseInWorkoutPlanModel(
                row[WorkoutPlanStrengthExerciseDao.exerciseId],
                row[WorkoutInWorkoutPlanDao.id],
                row[ExerciseDao.name],
                row[ExerciseDao.description],
                row[ExerciseDao.iconUri],
            )

            setModels += StrengthExerciseSetModel(
                row[WorkoutInWorkoutPlanDao.id],
                row[WorkoutPlanStrengthExerciseDao.exerciseId],
                row[WorkoutPlanStrengthExerciseDao.setNumber],
                row[WorkoutPlanStrengthExerciseDao.weight],
                row[WorkoutPlanStrengthExerciseDao.repetitionsNumber]
            )
        }

        workoutPlanModels = workoutPlanModels.distinct().toMutableList()
        workoutModels = workoutModels.distinct().toMutableList()
        exerciseModels = exerciseModels.distinct().toMutableList()
        setModels = setModels.distinct().toMutableList()

        return workoutPlanModels.map { workoutPlanModel ->
            WorkoutPlan(
                workoutPlanModel.id,
                workoutPlanModel.name,
                workoutPlanModel.description,
                workoutPlanModel.imageUri,
                getWorkoutByWorkoutPlanModel(workoutPlanModel, workoutModels, exerciseModels, setModels)
            )
        }
    }

    private fun getWorkoutByWorkoutPlanModel(
        workoutPlanModel: WorkoutPlanModel,
        workoutModels: List<WorkoutInWorkoutPlanModel>,
        exerciseModels: List<StrengthExerciseInWorkoutPlanModel>,
        setModels: List<StrengthExerciseSetModel>
    ): List<WorkoutInWorkoutPlan> =
        workoutModels.mapNotNull { workoutModel ->
            if (workoutModel.workoutPlanId == workoutPlanModel.id) {
                WorkoutInWorkoutPlan(
                    workoutModel.id,
                    workoutModel.name,
                    workoutModel.description ?: "",
                    workoutModel.imageUri ?: "",
                    getExercisesByWorkoutModel(workoutModel, exerciseModels, setModels)
                )
            } else {
                null
            }
        }


    private fun getExercisesByWorkoutModel(
        workoutModel: WorkoutInWorkoutPlanModel,
        exerciseModel: List<StrengthExerciseInWorkoutPlanModel>,
        setModels: List<StrengthExerciseSetModel>
    ): List<StrengthExerciseInWorkoutPlan> =
        exerciseModel.mapNotNull { strengthExerciseModel ->
            if (workoutModel.id == strengthExerciseModel.workoutId) {
                StrengthExerciseInWorkoutPlan(
                    strengthExerciseModel.id,
                    strengthExerciseModel.name,
                    strengthExerciseModel.description,
                    strengthExerciseModel.imageUri ?: "",
                    getSetsByExerciseModel(strengthExerciseModel, setModels)
                )
            } else {
                null
            }
        }


    private fun getSetsByExerciseModel(
        exerciseModel: StrengthExerciseInWorkoutPlanModel,
        setModels: List<StrengthExerciseSetModel>
    ): List<StrengthExerciseSet> =
        setModels.mapNotNull { strengthSetModel ->
            if (strengthSetModel.exerciseId == exerciseModel.id && strengthSetModel.workoutId == exerciseModel.workoutId) {
                StrengthExerciseSet(
                    strengthSetModel.index,
                    strengthSetModel.weight,
                    strengthSetModel.repetitionsNumber,
                )
            } else {
                null
            }
        }
}