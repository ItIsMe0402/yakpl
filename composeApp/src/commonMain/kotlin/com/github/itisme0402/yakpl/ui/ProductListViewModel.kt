package com.github.itisme0402.yakpl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.itisme0402.yakpl.domain.GetProductsUseCase
import com.github.itisme0402.yakpl.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    val searchQuery: StateFlow<String>
        field = MutableStateFlow("")

    val products: StateFlow<List<Product>> = searchQuery
        .mapLatest(getProductsUseCase::getProducts)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList(),
        )

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }
}
