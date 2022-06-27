package com.overmind.invoiceapp.android.ui.clients

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.overmind.invoiceapp.android.ui.clients.create.CreateClientScreen
import com.overmind.invoiceapp.android.ui.clients.edit.EditClientScreen
import com.overmind.invoiceapp.android.ui.clients.list.ClientsListScreen
import com.overmind.invoiceapp.android.ui.main.Screen

fun NavGraphBuilder.clientsGraph(navController: NavController) {
    navigation(startDestination = Screen.ClientsList.route, route = Screen.Clients.route) {
        composable(Screen.ClientsList.route) { ClientsListScreen(navController) }
        composable(Screen.CreateClient.route) { CreateClientScreen(navController) }
        composable(
            Screen.EditClient.route,
            arguments = listOf(navArgument("clientId") { type = NavType.IntType })
        ) { backStackEntry ->
            EditClientScreen(
                navController,
                clientId = backStackEntry.arguments!!.getInt("clientId")
            )
        }
    }
}
