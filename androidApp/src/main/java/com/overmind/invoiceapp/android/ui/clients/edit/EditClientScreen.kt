package com.overmind.invoiceapp.android.ui.clients.edit

import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.overmind.invoiceapp.android.ui.clients.ClientForm
import com.overmind.invoiceapp.android.ui.clients.emptyClient
import com.overmind.invoiceapp.domain.usecases.clients.ValidateClient
import org.koin.androidx.compose.get

@Composable
fun EditClientScreen(
    navController: NavController,
    clientId: Int,
    viewModel: EditClientViewModel = get()
) {
    LaunchedEffect(clientId) { viewModel.getClient(clientId) }

    val client = remember { mutableStateOf(emptyClient()) }
    val modifyResult = remember { mutableStateOf<ValidateClient.Result?>(null) }

    val uiState by viewModel.uiState.collectAsState()
    client.value = uiState.client

    ClientForm(
        title = "Edit client",
        client = client,
        result = modifyResult,
        onBack = { navController.navigateUp() },
        onSave = { modifyResult.value = viewModel.modifyClient(client.value) }
    )
}
