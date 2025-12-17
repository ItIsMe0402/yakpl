package com.github.itisme0402.yakpl.domain

import com.github.itisme0402.yakpl.OpenForMokkery
import com.github.itisme0402.yakpl.model.Product

@OpenForMokkery
class GetProductsUseCase(
    private val productRepository: ProductRepository,
) {
    suspend fun getProducts(query: String, skip: Int, limit: Int = 20): List<Product> {
        return productRepository.getProducts(query, skip, limit)
    }
}
