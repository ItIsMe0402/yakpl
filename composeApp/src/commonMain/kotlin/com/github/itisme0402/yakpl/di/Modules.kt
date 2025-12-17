package com.github.itisme0402.yakpl.di

import com.github.itisme0402.yakpl.domain.GetProductsUseCase
import com.github.itisme0402.yakpl.domain.ProductRepository
import com.github.itisme0402.yakpl.domain.ProductRepositoryImpl
import com.github.itisme0402.yakpl.domain.RemoteProductDataSource
import com.github.itisme0402.yakpl.network.KtorRemoteProductDataSource
import com.github.itisme0402.yakpl.network.createHttpClient
import com.github.itisme0402.yakpl.network.createJson
import com.github.itisme0402.yakpl.ui.ProductListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::ProductListViewModel)
}

val domainModule = module {
    singleOf(::GetProductsUseCase)
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class
}

val networkModule = module {
    singleOf(::KtorRemoteProductDataSource) bind RemoteProductDataSource::class
    singleOf(::createJson)
    singleOf(::createHttpClient)
}
