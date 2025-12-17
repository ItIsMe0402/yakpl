package com.github.itisme0402.yakpl.network

import com.github.itisme0402.yakpl.domain.RemoteProductDataSource
import com.github.itisme0402.yakpl.model.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorRemoteProductDataSource(
    private val httpClient: HttpClient,
) : RemoteProductDataSource {

    override suspend fun getProducts(query: String): List<Product> {
        val response: ProductListResponse = httpClient.get(ENDPOINT_SEARCH) {
            url {
                parameters.append(SEARCH_QUERY_PARAM_QUERY, query)
            }
        }.body()

        return response.products.map { it.asProduct }
    }

    private companion object Companion {
        const val ENDPOINT_SEARCH = "/products/search"
        const val SEARCH_QUERY_PARAM_QUERY = "q"
    }
}
