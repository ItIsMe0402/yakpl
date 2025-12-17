package com.github.itisme0402.yakpl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.itisme0402.yakpl.domain.GetProductUseCase
import com.github.itisme0402.yakpl.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: String,
    private val getProductUseCase: GetProductUseCase,
) : ViewModel() {

    val product: StateFlow<Product?>
        field = MutableStateFlow<Product?>(null)

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            try {
                product.value = getProductUseCase.getProduct(productId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
