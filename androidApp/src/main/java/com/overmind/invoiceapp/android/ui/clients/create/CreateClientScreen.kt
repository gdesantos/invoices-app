package com.overmind.invoiceapp.android.ui.clients.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.overmind.invoiceapp.android.ui.common.focusFixer
import com.overmind.invoiceapp.android.ui.common.focusFixerParent
import com.overmind.invoiceapp.android.ui.common.rememberFocusFixerData
import com.overmind.invoiceapp.domain.entities.Client
import com.overmind.invoiceapp.domain.usecases.ValidateClient
import org.koin.androidx.compose.get

@Composable
fun CreateClientScreen(navController: NavController, viewModel: CreateClientViewModel = get()) {
    val newClient = remember { mutableStateOf(Client(0, "", "", "", "", 0, "")) }
    val createResult = remember { mutableStateOf<ValidateClient.Result?>(null) }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val focusFixerData = rememberFocusFixerData()
    val coroutineScope = rememberCoroutineScope()

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

        Column(
            modifier =
                Modifier.focusFixerParent(focusFixerData, coroutineScope, scrollState)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ClientFormField(
                modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
                icon = Icons.Outlined.Person,
                label = "Name",
                value = newClient.value.name,
            ) {
                newClient.value = newClient.value.copy(name = it)
            }
            ClientFormField(
                modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
                icon = Icons.Outlined.RequestPage,
                label = "VAT",
                value = newClient.value.vat
            ) { newClient.value = newClient.value.copy(vat = it) }
            ClientFormField(
                modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
                icon = Icons.Outlined.HomeWork,
                label = "Address Line 1",
                value = newClient.value.addressLine1
            ) { newClient.value = newClient.value.copy(addressLine1 = it) }
            ClientFormField(
                modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
                icon = null,
                label = "Address Line 2",
                value = newClient.value.addressLine2
            ) { newClient.value = newClient.value.copy(addressLine2 = it) }
            ClientFormField(
                modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
                icon = Icons.Outlined.Phone,
                label = "Phone",
                value = newClient.value.phone.toString().takeIf { it != "0" } ?: "",
                keyboardOptions =
                    KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Phone),
            ) {
                newClient.value = newClient.value.copy(phone = it.toLongOrNull() ?: 0L)
            }
            ClientFormField(
                modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
                icon = Icons.Outlined.Email,
                label = "Email",
                value = newClient.value.email,
                keyboardOptions =
                    KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Email),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            ) { newClient.value = newClient.value.copy(email = it) }
        }
    }

    when (createResult.value) {
        null -> Unit
        ValidateClient.Result.Ok -> navController.navigateUp()
        else -> ErrorDialog(error = createResult)
    }
}

@Composable
private fun ClientFormField(
    modifier: Modifier = Modifier,
    icon: ImageVector?,
    label: String,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChanged: (String) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
        } else {
            Box(modifier = Modifier.size(24.dp))
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text(label) },
            value = value,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            onValueChange = onValueChanged
        )
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
