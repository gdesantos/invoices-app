package com.overmind.invoiceapp.android.ui.clients.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.overmind.invoiceapp.android.ui.clients.ClientForm
import com.overmind.invoiceapp.android.ui.clients.emptyClient
import com.overmind.invoiceapp.domain.usecases.clients.ValidateClient
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateClientScreen(
    navController: NavController,
    viewModel: CreateClientViewModel = getViewModel()
) {
    val newClient = remember { mutableStateOf(emptyClient()) }
    val createResult = remember { mutableStateOf<ValidateClient.Result?>(null) }

    ClientForm(
        title = "New client",
        client = newClient,
        result = createResult,
        onBack = { navController.navigateUp() },
        onSave = { createResult.value = viewModel.createClient(newClient.value) }
    )
}
