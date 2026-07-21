package com.stocksnap.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Inventory
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.lifecycleScope
import com.stocksnap.R
import com.stocksnap.data.repository.AuthRepository
import com.stocksnap.data.repository.ProductRepository
import com.stocksnap.presentation.navigation.StockSnapNavHost
import com.stocksnap.ui.theme.StockSnapTheme
import com.stocksnap.utils.MigrationUtility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var productRepository: ProductRepository

    @Inject
    lateinit var migrationUtility: MigrationUtility

    @Inject
    lateinit var networkConnectivityObserver: com.stocksnap.util.NetworkConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showSplash by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(1000)
                showSplash = false
            }

            val navController = rememberNavController()

            val user by authRepository.currentUser.collectAsState()
            LaunchedEffect(user) {
                if (user != null) {
                    productRepository.startRealtimeSync()
                    lifecycleScope.launch(Dispatchers.IO) {
                        migrationUtility.migrateExistingImages(applicationContext)
                    }
                    if (user!!.role == "EMPLOYEE") {
                        window.setFlags(
                            android.view.WindowManager.LayoutParams.FLAG_SECURE,
                            android.view.WindowManager.LayoutParams.FLAG_SECURE
                        )
                    } else {
                        window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE)
                    }
                } else {
                    productRepository.stopRealtimeSync()
                    // Clear flag on logout just in case
                    window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE)
                }
            }

            val isConnected by networkConnectivityObserver.networkStatus.collectAsState(initial = true)

            StockSnapTheme {
                Surface {
                    if (!isConnected) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            androidx.compose.foundation.layout.Column(
                                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = androidx.compose.material.icons.Icons.Rounded.Warning,
                                    contentDescription = "No Internet",
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    tint = androidx.compose.material3.MaterialTheme.colorScheme.error
                                )
                                Text(
                                    text = "No internet connection.",
                                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "Please connect to the internet to use the app.",
                                    modifier = Modifier.padding(top = 8.dp),
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    } else if (showSplash) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.loading_page_image),
                                contentDescription = "Splash Loading",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    } else {
                        StockSnapApp(
                            navController = navController,
                            authRepository = authRepository
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockSnapApp(
    navController: androidx.navigation.NavHostController,
    authRepository: AuthRepository
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Enforce hiding bottom nav bar when unauthenticated
    val showBottomBar = currentRoute != null && currentRoute !in setOf("login", "pendingApproval", "rejectedScreen", "disabledScreen", "scan")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                com.stocksnap.presentation.components.GlassNavigationBar(
                    items = listOf(
                        com.stocksnap.presentation.components.NavItem("Home", Icons.Rounded.Home, "dashboard"),
                        com.stocksnap.presentation.components.NavItem("Products", Icons.Rounded.Inventory, "products"),
                        com.stocksnap.presentation.components.NavItem("Activity", Icons.Rounded.History, "history"),
                        com.stocksnap.presentation.components.NavItem("Settings", Icons.Rounded.Settings, "settings")
                    ),
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        containerColor = com.stocksnap.ui.theme.AppBackground
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            color = Color.Transparent
        ) {
            StockSnapNavHost(
                navController = navController,
                authRepository = authRepository
            )
        }
    }
}
