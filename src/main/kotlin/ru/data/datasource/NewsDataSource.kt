package ru.data.datasource

import io.ktor.client.*
import io.ktor.client.request.*
import ru.data.entity.NewsResponse

interface NewsDataSource {

    suspend fun getList(): NewsResponse
}

class NewsDataSourceImpl(
    private val client: HttpClient
) : NewsDataSource {

    override suspend fun getList(): NewsResponse =
        client.get<NewsResponse> {
            url.encodedPath = "/top-headlines"
            parameter("country", "ru")
            parameter("category", "sport")
        }
}