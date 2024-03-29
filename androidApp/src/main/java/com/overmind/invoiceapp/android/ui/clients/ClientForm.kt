package com.overmind.invoiceapp.android.ui.clients

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.overmind.invoiceapp.android.ui.common.*
import com.overmind.invoiceapp.domain.entities.Client
import com.overmind.invoiceapp.domain.usecases.clients.ValidateClient

@Composable
fun ClientForm(
    title: String,
    client: MutableState<Client> = remember { mutableStateOf(emptyClient()) },
    result: MutableState<ValidateClient.Result?> = remember { mutableStateOf(null) },
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Column {
        ItemFormTopBar(title = title, onSave = onSave, onBack = onBack)
        ClientFormFields(client)
    }

    when (result.value) {
        null -> Unit
        ValidateClient.Result.Ok -> onBack()
        else -> ErrorDialog(error = result)
    }
}

@Composable
private fun ClientFormFields(client: MutableState<Client>) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val focusFixerData = rememberFocusFixerData()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier =
            Modifier.focusFixerParent(focusFixerData, coroutineScope, scrollState)
                .verticalScroll(scrollState)
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.Person,
            label = "Name",
            value = client.value.name,
        ) {
            client.value = client.value.copy(name = it)
        }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.RequestPage,
            label = "VAT",
            value = client.value.vat
        ) { client.value = client.value.copy(vat = it) }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.HomeWork,
            label = "Address Line 1",
            value = client.value.addressLine1
        ) { client.value = client.value.copy(addressLine1 = it) }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = null,
            label = "Address Line 2",
            value = client.value.addressLine2
        ) { client.value = client.value.copy(addressLine2 = it) }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.Phone,
            label = "Phone",
            value = client.value.phone,
            keyboardOptions =
                KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Phone),
        ) {
            client.value = client.value.copy(phone = it)
        }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.Email,
            label = "Email",
            value = client.value.email,
            keyboardOptions =
                KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        ) { client.value = client.value.copy(email = it) }
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
