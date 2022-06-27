package com.overmind.invoiceapp.android.ui.main

sealed class Screen(val route: String) {

    object Clients : Screen("clients")
    object ClientsList : Screen("clients_list")
    object CreateClient : Screen("create_client")
    object EditClient : Screen("edit_client/{clientId}") {
        fun forClient(clientId: Int)= "edit_client/$clientId"
    }

    object BusinessList : Screen("business_list")
    object ProductList : Screen("product_list")
    object Invoices : Screen("invoices")
}
