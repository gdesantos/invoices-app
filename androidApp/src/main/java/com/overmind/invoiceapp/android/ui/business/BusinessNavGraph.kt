package com.overmind.invoiceapp.android.ui.business

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.overmind.invoiceapp.android.ui.business.create.CreateBusinessScreen
import com.overmind.invoiceapp.android.ui.business.edit.EditBusinessScreen
import com.overmind.invoiceapp.android.ui.business.list.BusinessListScreen
import com.overmind.invoiceapp.android.ui.main.Screen

fun NavGraphBuilder.businessGraph(navController: NavController) {
    navigation(startDestination = Screen.BusinessList.route, route = Screen.Business.route) {
        composable(Screen.BusinessList.route) { BusinessListScreen(navController) }
        composable(Screen.CreateBusiness.route) { CreateBusinessScreen(navController) }
        composable(
            Screen.EditBusiness.route,
            arguments = listOf(navArgument("businessId") { type = NavType.IntType })
        ) { backStackEntry ->
            EditBusinessScreen(
                navController,
                businessId = backStackEntry.arguments!!.getInt("businessId")
            )
        }
    }
}