package com.github.itisme0402.yakpl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.itisme0402.yakpl.domain.GetProductsUseCase
import com.github.itisme0402.yakpl.model.Product
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    val searchQuery: StateFlow<String>
        field = MutableStateFlow("")

    val products: StateFlow<List<Product>>
        field = MutableStateFlow(emptyList())

    private var currentQuery = searchQuery.value
    private var currentSkip = 0
    private val limit = 20
    private var isLastPage = false
    private var isLoading = false
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .collectLatest { query ->
                    resetAndLoad(query)
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun loadMore() {
        if (isLoading || isLastPage) return
        loadProducts(append = true)
    }

    private fun resetAndLoad(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            currentQuery = query
            currentSkip = 0
            isLastPage = false
            products.value = emptyList()
            loadProducts(append = false)
        }
    }

    private fun loadProducts(append: Boolean) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val newProducts = getProductsUseCase.getProducts(currentQuery, currentSkip, limit)
                if (newProducts.size < limit) {
                    isLastPage = true
                }
                
                if (append) {
                    products.value += newProducts
                } else {
                    products.value = newProducts
                }
                
                if (newProducts.isNotEmpty()) {
                    currentSkip += limit
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                isLoading = false
            }
        }
    }
}
