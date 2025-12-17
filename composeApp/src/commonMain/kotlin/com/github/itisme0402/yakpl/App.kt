package com.github.itisme0402.yakpl

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.itisme0402.yakpl.ui.ProductDetailScreen
import com.github.itisme0402.yakpl.ui.ProductListScreen
import com.github.itisme0402.yakpl.ui.Route
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Route.Products) {
            composable<Route.Products> {
                ProductListScreen(
                    viewModel = koinViewModel(),
                    onProductClick = { product ->
                        navController.navigate(Route.Details(productId = product.id))
                    }
                )
            }
            composable<Route.Details> { backStackEntry ->
                val details: Route.Details = backStackEntry.toRoute()
                ProductDetailScreen(
                    viewModel = koinViewModel { parametersOf(details.productId) },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
