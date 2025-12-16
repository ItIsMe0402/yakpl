package com.github.itisme0402.yakpl

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.itisme0402.yakpl.ui.ProductListScreen
import com.github.itisme0402.yakpl.ui.Route
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Route.Products) {
            composable<Route.Products> {
                ProductListScreen(viewModel = koinViewModel())
            }
        }
    }
}
