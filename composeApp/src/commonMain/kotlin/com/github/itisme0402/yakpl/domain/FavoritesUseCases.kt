package com.github.itisme0402.yakpl.domain

import com.github.itisme0402.yakpl.model.Product
import kotlinx.coroutines.flow.Flow

class IsFavoriteUseCase(private val repository: FavoritesRepository) {
    operator fun invoke(productId: String): Flow<Boolean> = repository.isFavorite(productId)
}

class GetFavoritesUseCase(private val repository: FavoritesRepository) {
    operator fun invoke(): Flow<List<Product>> = repository.getFavorites()
}

class SetFavoriteUseCase(private val repository: FavoritesRepository) {
    suspend operator fun invoke(product: Product, isFavorite: Boolean) {
        if (isFavorite) {
            repository.addFavorite(product)
        } else {
            repository.removeFavorite(product.id)
        }
    }
}
