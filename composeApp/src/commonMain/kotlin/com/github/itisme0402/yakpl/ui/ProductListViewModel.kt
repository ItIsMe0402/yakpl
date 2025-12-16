package com.github.itisme0402.yakpl.ui

import androidx.lifecycle.ViewModel
import com.github.itisme0402.yakpl.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductListViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val allProducts = List(20) { Product(id = it.toString(), name = "Product $it", description = "This is product $it") }

    init {
        _products.value = allProducts
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _products.value = allProducts.filter { product ->
            product.name.contains(query, ignoreCase = true) || product.description.contains(query, ignoreCase = true)
        }
    }
}
