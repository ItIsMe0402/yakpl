package com.github.itisme0402.yakpl.domain

import com.github.itisme0402.yakpl.OpenForMokkery
import com.github.itisme0402.yakpl.model.Product

@OpenForMokkery
class GetProductUseCase(
    private val productRepository: ProductRepository,
) {
    suspend fun getProduct(id: String): Product {
        return productRepository.getProduct(id)
    }
}
