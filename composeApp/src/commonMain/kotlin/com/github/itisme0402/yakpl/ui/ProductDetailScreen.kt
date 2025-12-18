package com.github.itisme0402.yakpl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.itisme0402.yakpl.model.Product
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import yakpl.composeapp.generated.resources.Res
import yakpl.composeapp.generated.resources.ic_arrow_back
import yakpl.composeapp.generated.resources.ic_favorite
import yakpl.composeapp.generated.resources.loading

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    onBackClick: () -> Unit,
) {
    val product by viewModel.product.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    ProductDetailContent(
        product = product,
        isFavorite = isFavorite,
        onBackClick = onBackClick,
        onToggleFavorite = viewModel::toggleFavorite
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailContent(
    product: Product?,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.name ?: stringResource(Res.string.loading)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(painterResource(Res.drawable.ic_arrow_back), contentDescription = "Back")
                    }
                },
                actions = {
                    if (product != null) {
                        IconButton(onClick = onToggleFavorite) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_favorite),
                                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (product != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = product.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
