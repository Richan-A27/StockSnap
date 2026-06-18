package com.stocksnap.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stocksnap.presentation.dashboard.DashboardScreen
import com.stocksnap.presentation.history.HistoryScreen
import com.stocksnap.presentation.review.ReviewScreen
import com.stocksnap.presentation.review.ReviewViewModel
import com.stocksnap.presentation.scan.ScanScreen
import com.stocksnap.presentation.settings.SettingsScreen

@Composable
fun StockSnapNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(onNewScan = { navController.navigate("scan") }, onProductClick = { id -> navController.navigate("details/$id") }) }
        composable("scan") { ScanScreen(onReview = { id -> navController.navigate("review/$id") }) }
        composable(
            route = "review/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->
            val viewModel: ReviewViewModel = hiltViewModel(backStackEntry)
            ReviewScreen(onSave = { navController.popBackStack("dashboard", false) }, viewModel = viewModel)
        }
        composable("history") { 
            HistoryScreen(onProductClick = { id -> navController.navigate("details/$id") }) 
        }
        composable("settings") { 
            SettingsScreen() 
        }
        composable(
            route = "details/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->
            val viewModel: com.stocksnap.presentation.details.DetailsViewModel = hiltViewModel(backStackEntry)
            com.stocksnap.presentation.details.DetailsScreen(onBack = { navController.popBackStack() }, viewModel = viewModel)
        }
    }
}
