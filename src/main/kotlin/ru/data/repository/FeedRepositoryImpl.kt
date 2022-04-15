package ru.data.repository

import ru.data.converter.NewsConverter
import ru.data.datasource.ExerciseDataSource
import ru.data.datasource.NewsDataSource
import ru.data.datasource.WorkoutPlanDataSource
import ru.domain.entity.feed.Feed
import ru.domain.repository.FeedRepository

class FeedRepositoryImpl(
    private val newsDataSource: NewsDataSource,
    private val exerciseDataSource: ExerciseDataSource,
    private val workoutPlanDataSource: WorkoutPlanDataSource,
    private val newsConverter: NewsConverter,
): FeedRepository {

    override suspend fun get(count: Int): Feed {
        val workoutPlans = workoutPlanDataSource.getList().take(count)
        val exercises = exerciseDataSource.getList().take(count)
        val news = try {
            newsConverter.convert(newsDataSource.getList()).take(count)
        } catch (e: Exception) {
            emptyList()
        }

        return Feed(
            workoutPlans,
            exercises,
            news
        )
    }
}