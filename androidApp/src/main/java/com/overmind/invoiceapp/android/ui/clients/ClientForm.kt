package com.overmind.invoiceapp.android.ui.clients

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.overmind.invoiceapp.android.ui.common.focusFixer
import com.overmind.invoiceapp.android.ui.common.focusFixerParent
import com.overmind.invoiceapp.android.ui.common.rememberFocusFixerData
import com.overmind.invoiceapp.domain.entities.Client

@Composable
fun ClientForm(client: MutableState<Client> = remember { mutableStateOf(emptyClient()) }) {
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
        ClientFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.Person,
            label = "Name",
            value = client.value.name,
        ) {
            client.value = client.value.copy(name = it)
        }
        ClientFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.RequestPage,
            label = "VAT",
            value = client.value.vat
        ) { client.value = client.value.copy(vat = it) }
        ClientFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.HomeWork,
            label = "Address Line 1",
            value = client.value.addressLine1
        ) { client.value = client.value.copy(addressLine1 = it) }
        ClientFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = null,
            label = "Address Line 2",
            value = client.value.addressLine2
        ) { client.value = client.value.copy(addressLine2 = it) }
        ClientFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.Phone,
            label = "Phone",
            value = client.value.phone.toString().takeIf { it != "0" } ?: "",
            keyboardOptions =
            KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Phone),
        ) {
            client.value = client.value.copy(phone = it.toLongOrNull() ?: 0L)
        }
        ClientFormField(
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