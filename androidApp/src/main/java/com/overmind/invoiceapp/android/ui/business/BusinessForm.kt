package com.overmind.invoiceapp.android.ui.business

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.overmind.invoiceapp.android.ui.common.*
import com.overmind.invoiceapp.domain.entities.Business
import com.overmind.invoiceapp.domain.usecases.business.AddBusiness

@Composable
fun BusinessForm(
    title: String,
    business: MutableState<Business> = remember { mutableStateOf(emptyBusiness()) },
    result: MutableState<AddBusiness.Result?> = remember { mutableStateOf(null) },
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Column {
        ItemFormTopBar(title = title, onSave = onSave, onBack = onBack)
        BusinessFormFields(business)
    }

    when (result.value) {
        null -> Unit
        AddBusiness.Result.Ok -> onBack()
        else -> ErrorDialog(error = result)
    }
}

@Composable
private fun BusinessFormFields(business: MutableState<Business>) {
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
            value = business.value.name,
        ) {
            business.value = business.value.copy(name = it)
        }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.RequestPage,
            label = "VAT",
            value = business.value.vat
        ) { business.value = business.value.copy(vat = it) }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.HomeWork,
            label = "Address Line 1",
            value = business.value.addressLine1
        ) { business.value = business.value.copy(addressLine1 = it) }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = null,
            label = "Address Line 2",
            value = business.value.addressLine2
        ) { business.value = business.value.copy(addressLine2 = it) }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.Phone,
            label = "Phone",
            value = business.value.phone,
            keyboardOptions =
            KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Phone),
        ) {
            business.value = business.value.copy(phone = it)
        }
        ItemFormField(
            modifier = Modifier.focusFixer(focusFixerData, coroutineScope, scrollState),
            icon = Icons.Outlined.Email,
            label = "Email",
            value = business.value.email,
            keyboardOptions =
            KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        ) { business.value = business.value.copy(email = it) }
    }
}

@Composable
private fun ErrorDialog(error: MutableState<AddBusiness.Result?>) {
    val message =
        when (error.value) {
            AddBusiness.Result.InvalidName -> "Invalid name"
            AddBusiness.Result.InvalidVat -> "Invalid VAT"
            AddBusiness.Result.InvalidAddressLine1 -> "Invalid address"
            AddBusiness.Result.InvalidPhone -> "Invalid phone"
            AddBusiness.Result.InvalidEmail -> "Invalid email"
            AddBusiness.Result.LimitReached -> "Too many business"
            AddBusiness.Result.Ok, null -> ""
        }

    AlertDialog(
        text = { Text(message) },
        onDismissRequest = { error.value = null },
        confirmButton = { TextButton(onClick = { error.value = null }) { Text("Ok") } }
    )
}