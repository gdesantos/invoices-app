package com.overmind.invoiceapp.android.ui.clients.edit

import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.overmind.invoiceapp.android.ui.clients.ClientForm
import com.overmind.invoiceapp.android.ui.clients.emptyClient
import org.koin.androidx.compose.get

@Composable
fun EditClientScreen(
    navController: NavController,
    clientId: Int,
    viewModel: EditClientViewModel = get()
) {
    LaunchedEffect(clientId) { viewModel.getClient(clientId) }

    val client = remember { mutableStateOf(emptyClient()) }
    val uiState by viewModel.uiState.collectAsState()
    client.value = uiState.client

    ClientForm(client)
}
