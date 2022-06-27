package com.overmind.invoiceapp.android.ui.clients.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.overmind.invoiceapp.android.ui.clients.ClientForm
import com.overmind.invoiceapp.android.ui.clients.emptyClient
import com.overmind.invoiceapp.domain.usecases.ValidateClient
import org.koin.androidx.compose.get

@Composable
fun CreateClientScreen(navController: NavController, viewModel: CreateClientViewModel = get()) {
    val newClient = remember { mutableStateOf(emptyClient()) }
    val createResult = remember { mutableStateOf<ValidateClient.Result?>(null) }

    Column {
        TopAppBar(
            title = { Text("Create Client") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(
                    onClick = { createResult.value = viewModel.createClient(newClient.value) }
                ) { Icon(imageVector = Icons.Default.Save, contentDescription = "Save") }
            }
        )

        ClientForm(client = newClient)
    }

    when (createResult.value) {
        null -> Unit
        ValidateClient.Result.Ok -> navController.navigateUp()
        else -> ErrorDialog(error = createResult)
    }
}

@Composable
private fun ErrorDialog(error: MutableState<ValidateClient.Result?>) {
    val message =
        when (error.value) {
            ValidateClient.Result.InvalidName -> "Invalid name"
            ValidateClient.Result.InvalidVat -> "Invalid VAT"
            ValidateClient.Result.InvalidAddressLine1 -> "Invalid address"
            ValidateClient.Result.InvalidPhone -> "Invalid phone"
            ValidateClient.Result.InvalidEmail -> "Invalid email"
            else -> ""
        }

    AlertDialog(
        text = { Text(message) },
        onDismissRequest = { error.value = null },
        confirmButton = { TextButton(onClick = { error.value = null }) { Text("Ok") } }
    )
}
