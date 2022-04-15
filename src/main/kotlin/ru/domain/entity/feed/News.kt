package ru.domain.entity.feed

data class News(
    val title: String,
    val imageUri: String,
    val description: String,
    val sourceUrl: String,
    val time: Long,
)
