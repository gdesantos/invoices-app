package com.overmind.invoiceapp.android.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ItemFormTopBar(title: String, onSave: () -> Unit, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(
                onClick = { onSave() }
            ) { Icon(imageVector = Icons.Default.Save, contentDescription = "Save") }
        }
    )
}

@Composable
fun ItemFormField(
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