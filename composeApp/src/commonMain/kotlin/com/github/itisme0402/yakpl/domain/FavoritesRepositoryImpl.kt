package com.github.itisme0402.yakpl.domain

import com.github.itisme0402.yakpl.db.FavoriteEntity
import com.github.itisme0402.yakpl.db.FavoritesDao
import com.github.itisme0402.yakpl.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val favoritesDao: FavoritesDao
) : FavoritesRepository {

    override fun getFavorites(): Flow<List<Product>> {
        return favoritesDao.getAllFavorites().map { entities ->
            entities.map { entity ->
                Product(
                    id = entity.id,
                    name = entity.name,
                    description = entity.description
                )
            }
        }
    }

    override fun isFavorite(productId: String): Flow<Boolean> {
        return favoritesDao.isFavorite(productId)
    }

    override suspend fun addFavorite(product: Product) {
        favoritesDao.addFavorite(
            FavoriteEntity(
                id = product.id,
                name = product.name,
                description = product.description
            )
        )
    }

    override suspend fun removeFavorite(productId: String) {
        favoritesDao.removeFavoriteById(productId)
    }
}
