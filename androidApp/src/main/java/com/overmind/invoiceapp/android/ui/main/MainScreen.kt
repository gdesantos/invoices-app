package com.overmind.invoiceapp.android.ui.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.overmind.invoiceapp.android.R
import com.overmind.invoiceapp.android.ui.WipScreen
import com.overmind.invoiceapp.android.ui.clients.clientsGraph
import org.koin.androidx.compose.get

@Composable
fun MainScreen(viewModel: MainViewModel = get()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                Tab(
                    tabInfo = TabInfo.Clients,
                    uiState.value.clientsTabEnabled,
                    navController,
                    navBackStackEntry
                )
                Tab(
                    tabInfo = TabInfo.Business,
                    uiState.value.businessTabEnabled,
                    navController,
                    navBackStackEntry
                )
                Tab(
                    tabInfo = TabInfo.Products,
                    uiState.value.productsTabEnabled,
                    navController,
                    navBackStackEntry
                )
                Tab(
                    tabInfo = TabInfo.Invoices,
                    uiState.value.invoicesTabEnabled,
                    navController,
                    navBackStackEntry
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Clients.route
        ) {
            clientsGraph(navController)
            composable(Screen.BusinessList.route) { WipScreen() }
            composable(Screen.ProductList.route) { WipScreen() }
            composable(Screen.Invoices.route) { WipScreen() }
        }
    }
}

@Composable
private fun RowScope.Tab(
    tabInfo: TabInfo,
    enabled: Boolean,
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?
) {
    BottomNavigationItem(
        icon = { Icon(painterResource(id = tabInfo.icon), contentDescription = null) },
        label = { Text(tabInfo.title) },
        selected =
            navBackStackEntry?.destination?.hierarchy?.any { it.route == tabInfo.route } == true,
        enabled = enabled,
        selectedContentColor = Color.White,
        unselectedContentColor = Color.White.copy(alpha = 0.4f),
        onClick = {
            navController.navigate(tabInfo.route) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

private enum class TabInfo(val title: String, @DrawableRes val icon: Int, val route: String) {
    Clients("Clients", R.drawable.ic_clients, Screen.Clients.route),
    Business("Business", R.drawable.ic_business, Screen.BusinessList.route),
    Products("Products", R.drawable.ic_products, Screen.ProductList.route),
    Invoices("Invoices", R.drawable.ic_invoices, Screen.Invoices.route)
}
