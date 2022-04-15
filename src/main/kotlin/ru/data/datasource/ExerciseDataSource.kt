package ru.data.datasource

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.data.dao.ExerciseDao
import ru.domain.entity.exercise.Exercise

interface ExerciseDataSource {

    fun getList(): List<Exercise>
}

class ExerciseDataSourceImpl : ExerciseDataSource {

    override fun getList(): List<Exercise> = transaction {
        ExerciseDao.selectAll().map { row ->
            Exercise(
                row[ExerciseDao.id],
                row[ExerciseDao.name],
                row[ExerciseDao.description],
                row[ExerciseDao.iconUri] ?: ""
            )
        }
    }
}