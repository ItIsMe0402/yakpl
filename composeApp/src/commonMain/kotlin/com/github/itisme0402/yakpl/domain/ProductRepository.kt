package com.github.itisme0402.yakpl.domain

import com.github.itisme0402.yakpl.model.Product

interface ProductRepository {
    suspend fun getProducts(query: String, skip: Int, limit: Int): List<Product>
}
