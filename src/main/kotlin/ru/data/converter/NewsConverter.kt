package ru.data.converter

import ru.data.model.NewsResponse
import ru.domain.entity.feed.News
import java.text.SimpleDateFormat

class NewsConverter {

    private companion object {

        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    }

    fun convert(from: NewsResponse): List<News> {
        val newsList = mutableListOf<News>()
        from.articles.forEach {
            if (it.imageUri == null || it.description == null) {
                return@forEach
            }
            newsList.add(
                News(
                    it.title,
                    it.imageUri,
                    it.description,
                    it.sourceUrl,
                    it.publishedAt.time,
                )
            )
        }
        return newsList
    }


    private fun formatTime(from: String): Long {
        val format = SimpleDateFormat(DATE_FORMAT)
        return format.parse(from).time
    }
}