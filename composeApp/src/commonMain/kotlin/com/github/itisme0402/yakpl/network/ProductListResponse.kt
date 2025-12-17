package com.github.itisme0402.yakpl.network

import com.github.itisme0402.yakpl.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    val products: List<RemoteProduct>,
    val total: Int,
    val skip: Int,
    val limit: Int,
)
