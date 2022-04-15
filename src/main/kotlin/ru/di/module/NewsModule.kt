package ru.di.module

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.dsl.module
import ru.data.converter.NewsConverter
import ru.data.datasource.NewsDataSource
import ru.data.datasource.NewsDataSourceImpl
import ru.util.envConfig

private const val BASE_URL = "https://newsapi.org/v2"

val newsModule = module {
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }

        defaultRequest {
            url.takeFrom(URLBuilder().takeFrom(BASE_URL).apply {
                encodedPath += url.encodedPath
            })
            parameter("apiKey", envConfig.property("news.apiKey").getString())
        }
    }

    single<NewsDataSource> { NewsDataSourceImpl(client) }
    single { NewsConverter() }
}