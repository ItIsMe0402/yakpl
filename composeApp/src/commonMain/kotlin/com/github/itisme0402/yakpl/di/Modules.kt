package com.github.itisme0402.yakpl.di

import com.github.itisme0402.yakpl.domain.GetProductsUseCase
import com.github.itisme0402.yakpl.ui.ProductListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::ProductListViewModel)
}

val domainModule = module {
    singleOf(::GetProductsUseCase)
}
