package com.github.itisme0402.yakpl.domain

import com.github.itisme0402.yakpl.model.Product
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavorites(): Flow<List<Product>>
    fun isFavorite(productId: String): Flow<Boolean>
    suspend fun addFavorite(product: Product)
    suspend fun removeFavorite(productId: String)
}
