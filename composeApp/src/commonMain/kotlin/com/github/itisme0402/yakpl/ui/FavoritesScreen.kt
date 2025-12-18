package com.github.itisme0402.yakpl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.itisme0402.yakpl.model.Product
import org.jetbrains.compose.resources.painterResource
import yakpl.composeapp.generated.resources.Res
import yakpl.composeapp.generated.resources.ic_arrow_back

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onProductClick: (Product) -> Unit,
    onBackClick: () -> Unit
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    FavoritesScreenContent(
        favorites = favorites,
        onProductClick = onProductClick,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
    favorites: List<Product>,
    onProductClick: (Product) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(painterResource(Res.drawable.ic_arrow_back), contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No favorites yet", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(favorites) { product ->
                    ProductListItem(
                        product = product,
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}
