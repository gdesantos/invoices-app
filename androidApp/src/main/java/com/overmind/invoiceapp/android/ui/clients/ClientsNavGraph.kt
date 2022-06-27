package com.overmind.invoiceapp.android.ui.clients

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.overmind.invoiceapp.android.ui.clients.create.CreateClientScreen
import com.overmind.invoiceapp.android.ui.clients.list.ClientsScreen
import com.overmind.invoiceapp.android.ui.main.Screen

fun NavGraphBuilder.clientsGraph(navController: NavController) {
    navigation(startDestination = Screen.ClientsList.route, route = Screen.Clients.route) {
        composable(Screen.ClientsList.route) { ClientsScreen(navController = navController) }
        composable(Screen.CreateClient.route) { CreateClientScreen(navController = navController) }
    }
}
