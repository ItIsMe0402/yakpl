package com.github.itisme0402.yakpl.domain

import com.github.itisme0402.yakpl.OpenForMokkery
import com.github.itisme0402.yakpl.model.Product

@OpenForMokkery
class GetProductsUseCase {
    private val allProducts = List(20) { Product(id = it.toString(), name = "Product $it", description = "This is product $it") }

    suspend fun getProducts(query: String): List<Product> {
        return allProducts.filter { product ->
            product.name.contains(query, ignoreCase = true) || product.description.contains(query, ignoreCase = true)
        }
    }
}
