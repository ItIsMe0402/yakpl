package com.github.itisme0402.yakpl.di

import com.github.itisme0402.yakpl.ui.ProductListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::ProductListViewModel)
}
