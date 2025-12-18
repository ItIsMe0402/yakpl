package com.github.itisme0402.yakpl.ui

import androidx.compose.runtime.Composable
import com.github.itisme0402.yakpl.model.Product
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ProductListScreenPreview() {
    ProductListScreen(
        searchQuery = "Search query",
        products = (1..12).map {
            Product(it.toString(), "Product $it", "Description $it")
        },
        onSearchQueryChanged = {},
        onLoadMore = {},
        onProductClick = {},
        onFavoritesClick = {},
    )
}
