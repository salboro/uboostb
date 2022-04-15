package ru.data.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<NewsModel>
)

data class NewsModel(
    @SerializedName("title")
    val title: String,

    @SerializedName("urlToImage")
    val imageUri: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("url")
    val sourceUrl: String,

    @SerializedName("publishedAt")
    val publishedAt: Date,
)
