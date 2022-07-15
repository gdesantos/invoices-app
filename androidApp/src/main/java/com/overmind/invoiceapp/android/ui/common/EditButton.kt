package com.overmind.invoiceapp.android.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable

@Composable
fun EditButton(onEdit: () -> Unit) {
    IconButton(onClick = { onEdit() }) {
        Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit")
    }
}