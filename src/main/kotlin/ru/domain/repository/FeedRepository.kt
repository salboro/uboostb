package ru.domain.repository

import ru.domain.entity.feed.Feed

interface FeedRepository {

    suspend fun get(count: Int = 5): Feed
}