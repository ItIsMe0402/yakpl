package com.github.itisme0402.yakpl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.itisme0402.yakpl.domain.GetFavoritesUseCase
import com.github.itisme0402.yakpl.model.Product
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(
    getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    val favorites: StateFlow<List<Product>> = getFavoritesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
