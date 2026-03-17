package com.efimov.toolshop.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.efimov.toolshop.presentation.screens.AuthScreen
import com.efimov.toolshop.presentation.screens.CartScreen
import com.efimov.toolshop.presentation.screens.CatalogScreen
import com.efimov.toolshop.presentation.screens.CheckoutScreen
import com.efimov.toolshop.presentation.screens.OrderDetailScreen
import com.efimov.toolshop.presentation.screens.OrderHistoryScreen
import com.efimov.toolshop.presentation.screens.PaymentScreen
import com.efimov.toolshop.presentation.screens.ProductDetailScreen
import com.efimov.toolshop.presentation.screens.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = "catalog"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("catalog") {
            CatalogScreen(navController)
        }
        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            ProductDetailScreen(productId = productId, navController = navController)
        }
        composable("cart") {
            CartScreen(navController)
        }
        composable("checkout") {
            CheckoutScreen(navController)
        }
        composable("payment/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")
            PaymentScreen(orderId = orderId, navController = navController)
        }
        composable("orders") {
            OrderHistoryScreen(navController)
        }
        composable("order/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")
            OrderDetailScreen(orderId = orderId, navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("auth") {
            AuthScreen(navController)
        }
    }
}