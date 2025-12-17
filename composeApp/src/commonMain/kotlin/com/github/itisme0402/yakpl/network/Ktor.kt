package com.github.itisme0402.yakpl.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createJson(): Json {
    return Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
}

fun createHttpClient(json: Json): HttpClient {
    return HttpClient {
        defaultRequest {
            url(BASE_URL)
        }
        install(ContentNegotiation) {
            json(json)
        }
    }
}

private const val BASE_URL = "https://dummyjson.com"
