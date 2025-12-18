package com.github.itisme0402.yakpl.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.itisme0402.yakpl.model.Product
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import yakpl.composeapp.generated.resources.Res
import yakpl.composeapp.generated.resources.ic_favorite
import yakpl.composeapp.generated.resources.ic_search
import yakpl.composeapp.generated.resources.search
import yakpl.composeapp.generated.resources.title_products

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onProductClick: (Product) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val products by viewModel.products.collectAsStateWithLifecycle()

    ProductListScreen(
        searchQuery = searchQuery,
        products = products,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onLoadMore = viewModel::loadMore,
        onProductClick = onProductClick,
        onFavoritesClick = onFavoritesClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    searchQuery: String,
    products: List<Product>,
    onSearchQueryChanged: (String) -> Unit,
    onLoadMore: () -> Unit,
    onProductClick: (Product) -> Unit,
    onFavoritesClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.title_products)) },
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(painterResource(Res.drawable.ic_favorite), contentDescription = "Favorites")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text(stringResource(Res.string.search)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_search),
                        contentDescription = null,
                    )
                }
            )

            val listState = rememberLazyListState()
            
            // Check if we reached the bottom
            val shouldLoadMore by remember {
                derivedStateOf {
                    val layoutInfo = listState.layoutInfo
                    val totalItemsNumber = layoutInfo.totalItemsCount
                    val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

                    lastVisibleItemIndex > (totalItemsNumber - 5)
                }
            }

            LaunchedEffect(shouldLoadMore) {
                if (shouldLoadMore) {
                    onLoadMore()
                }
            }

            LazyColumn(state = listState) {
                items(products) { product ->
                    ProductListItem(
                        product = product,
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductListItem(
    product: Product,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(text = product.name, style = MaterialTheme.typography.titleLarge)
        Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
    }
}
