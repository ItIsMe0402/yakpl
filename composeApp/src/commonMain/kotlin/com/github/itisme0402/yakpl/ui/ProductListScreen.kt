package com.github.itisme0402.yakpl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.itisme0402.yakpl.model.Product
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import yakpl.composeapp.generated.resources.Res
import yakpl.composeapp.generated.resources.ic_search
import yakpl.composeapp.generated.resources.search
import yakpl.composeapp.generated.resources.title_products

@Composable
fun ProductListScreen(viewModel: ProductListViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val products by viewModel.products.collectAsState()

    ProductListScreen(
        searchQuery = searchQuery,
        products = products,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onLoadMore = viewModel::loadMore,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    searchQuery: String,
    products: List<Product>,
    onSearchQueryChanged: (String) -> Unit,
    onLoadMore: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.title_products)) }
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
                    ProductListItem(product)
                }
            }
        }
    }
}

@Composable
fun ProductListItem(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = product.name, style = MaterialTheme.typography.titleLarge)
        Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
    }
}
