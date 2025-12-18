package com.github.itisme0402.yakpl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.itisme0402.yakpl.domain.GetProductUseCase
import com.github.itisme0402.yakpl.domain.IsFavoriteUseCase
import com.github.itisme0402.yakpl.domain.SetFavoriteUseCase
import com.github.itisme0402.yakpl.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: String,
    private val getProductUseCase: GetProductUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
) : ViewModel() {

    val product: StateFlow<Product?>
        field = MutableStateFlow<Product?>(null)

    val isFavorite: StateFlow<Boolean> = isFavoriteUseCase(productId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )

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

    fun toggleFavorite() {
        val currentProduct = product.value ?: return
        viewModelScope.launch {
            setFavoriteUseCase(currentProduct, !isFavorite.value)
        }
    }
}
