package com.github.itisme0402.yakpl.di

import com.github.itisme0402.yakpl.db.AppDatabase
import com.github.itisme0402.yakpl.db.FavoritesDao
import com.github.itisme0402.yakpl.db.getRoomDatabase
import com.github.itisme0402.yakpl.domain.FavoritesRepositoryImpl
import com.github.itisme0402.yakpl.domain.FavoritesRepository
import com.github.itisme0402.yakpl.domain.GetFavoritesUseCase
import com.github.itisme0402.yakpl.domain.GetProductUseCase
import com.github.itisme0402.yakpl.domain.GetProductsUseCase
import com.github.itisme0402.yakpl.domain.IsFavoriteUseCase
import com.github.itisme0402.yakpl.domain.ProductRepository
import com.github.itisme0402.yakpl.domain.ProductRepositoryImpl
import com.github.itisme0402.yakpl.domain.RemoteProductDataSource
import com.github.itisme0402.yakpl.domain.SetFavoriteUseCase
import com.github.itisme0402.yakpl.network.KtorRemoteProductDataSource
import com.github.itisme0402.yakpl.network.createHttpClient
import com.github.itisme0402.yakpl.network.createJson
import com.github.itisme0402.yakpl.ui.FavoritesViewModel
import com.github.itisme0402.yakpl.ui.ProductDetailViewModel
import com.github.itisme0402.yakpl.ui.ProductListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::ProductListViewModel)
    viewModelOf(::ProductDetailViewModel)
    viewModelOf(::FavoritesViewModel)
}

val domainModule = module {
    singleOf(::GetProductsUseCase)
    singleOf(::GetProductUseCase)
    singleOf(::IsFavoriteUseCase)
    singleOf(::GetFavoritesUseCase)
    singleOf(::SetFavoriteUseCase)
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class
    singleOf(::FavoritesRepositoryImpl) bind FavoritesRepository::class
}

val databaseModule = module {
    single<AppDatabase> { getRoomDatabase(get()) }
    single<FavoritesDao> { get<AppDatabase>().favoritesDao() }
}

val networkModule = module {
    singleOf(::KtorRemoteProductDataSource) bind RemoteProductDataSource::class
    singleOf(::createJson)
    singleOf(::createHttpClient)
}
