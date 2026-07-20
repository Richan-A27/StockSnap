package com.stocksnap.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stocksnap.data.repository.AuthRepository
import com.stocksnap.presentation.auth.AccountStatusScreen
import com.stocksnap.presentation.auth.LoginScreen
import com.stocksnap.presentation.dashboard.DashboardScreen
import com.stocksnap.presentation.details.DetailsScreen
import com.stocksnap.presentation.edit.EditArrivalScreen
import com.stocksnap.presentation.edit.EditProductScreen
import com.stocksnap.presentation.history.HistoryScreen
import com.stocksnap.presentation.scan.ScanScreen
import com.stocksnap.presentation.products.ProductsScreen
import com.stocksnap.presentation.settings.SettingsScreen
import kotlinx.coroutines.launch

/**
 * Resolves the correct destination for a given user state.
 */
private fun resolveDestination(user: com.stocksnap.data.model.User?): String {
    return when {
        user == null -> "login"
        user.approvalStatus == "PENDING" -> "pendingApproval"
        user.approvalStatus == "REJECTED" -> "rejectedScreen"
        user.approvalStatus == "APPROVED" && !user.active -> "disabledScreen"
        else -> "dashboard"
    }
}

@Composable
fun StockSnapNavHost(
    navController: NavHostController,
    authRepository: AuthRepository
) {
    val currentUser by authRepository.currentUser.collectAsState()
    val scope = rememberCoroutineScope()

    // Compute startDestination ONCE from the current StateFlow value.
    // If the user is already signed in (session persisted), this will be
    // "dashboard" directly, avoiding any login screen flash.
    val startDest = remember {
        resolveDestination(authRepository.currentUser.value)
    }

    // React to ALL user state changes and navigate accordingly.
    // This handles: sign-in, sign-out, approval changes, app restart with session.
    LaunchedEffect(currentUser?.uid, currentUser?.approvalStatus, currentUser?.active) {
        val dest = resolveDestination(currentUser)
        val currentRoute = navController.currentDestination?.route ?: return@LaunchedEffect

        // Don't navigate if already on the correct screen
        if (currentRoute == dest) return@LaunchedEffect

        // Don't navigate away from content screens (details, edit, scan, etc.)
        // Only auto-navigate from auth/status screens
        val autoNavigableRoutes = setOf("login", "pendingApproval", "rejectedScreen", "disabledScreen")
        if (currentRoute !in autoNavigableRoutes) {
            // Exception: if user signed out, always go to login
            if (currentUser == null) {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            }
            return@LaunchedEffect
        }

        // Navigate to the correct destination
        navController.navigate(dest) {
            popUpTo(0) { inclusive = true }
        }
    }

    NavHost(navController = navController, startDestination = startDest) {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                // Read directly from StateFlow (not Compose state) to avoid stale reads
                val user = authRepository.currentUser.value
                val dest = resolveDestination(user)
                navController.navigate(dest) {
                    popUpTo("login") { inclusive = true }
                }
            })
        }

        // Pending Approval Screen
        composable("pendingApproval") {
            AccountStatusScreen(
                statusType = "PENDING",
                onLogout = {
                    scope.launch {
                        authRepository.signOut()
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                onRefresh = {
                    scope.launch {
                        authRepository.refreshCurrentUser()
                        // LaunchedEffect will handle navigation if status changed
                    }
                }
            )
        }

        // Rejected Screen
        composable("rejectedScreen") {
            AccountStatusScreen(
                statusType = "REJECTED",
                onLogout = {
                    scope.launch {
                        authRepository.signOut()
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            )
        }

        // Disabled Screen
        composable("disabledScreen") {
            AccountStatusScreen(
                statusType = "DISABLED",
                onLogout = {
                    scope.launch {
                        authRepository.signOut()
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                onBarcodeScan = { navController.navigate("scan") },
                onArrivalClick = { arrivalId -> navController.navigate("details/$arrivalId") },
                onViewAllDeliveries = { navController.navigate("history") },
                onViewAllActivity = { navController.navigate("activityLogs") }
            )
        }
        composable("activityLogs") {
            com.stocksnap.presentation.activity.ActivityLogsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable("products") {
            ProductsScreen(
                onProductClick = { productId -> navController.navigate("editProduct/$productId") }
            )
        }
        composable("scan") {
            ScanScreen(onReview = {
                navController.navigate("dashboard") {
                    popUpTo("dashboard") { inclusive = false }
                }
            })
        }

        composable("history") {
            HistoryScreen(onArrivalClick = { arrivalId -> navController.navigate("details/$arrivalId") })
        }
        composable("settings") {
            SettingsScreen(
                onNavigateToUserManagement = { navController.navigate("userManagement") }
            )
        }
        composable("userManagement") {
            if (currentUser?.role == "ADMIN") {
                com.stocksnap.presentation.admin.UserManagementScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = "details/{arrivalId}",
            arguments = listOf(navArgument("arrivalId") { type = NavType.StringType })
        ) {
            DetailsScreen(
                onBack = { navController.popBackStack() },
                onEditProduct = { productId -> navController.navigate("editProduct/$productId") }
            )
        }
        composable(
            route = "editProduct/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) {
            EditProductScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = "editArrival/{arrivalId}",
            arguments = listOf(navArgument("arrivalId") { type = NavType.StringType })
        ) {
            EditArrivalScreen(onBack = { navController.popBackStack() })
        }
    }
}
