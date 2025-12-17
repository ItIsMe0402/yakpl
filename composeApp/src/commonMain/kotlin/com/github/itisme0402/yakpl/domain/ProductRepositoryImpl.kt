package com.github.itisme0402.yakpl.domain

import com.github.itisme0402.yakpl.model.Product

class ProductRepositoryImpl(
    private val remoteProductDataSource: RemoteProductDataSource,
) : ProductRepository {

    override suspend fun getProducts(query: String, skip: Int, limit: Int): List<Product> {
        return remoteProductDataSource.getProducts(query, skip, limit)
    }

    override suspend fun getProduct(id: String): Product {
        return remoteProductDataSource.getProduct(id)
    }
}
