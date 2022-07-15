package com.overmind.invoiceapp.android.ui.common

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*

@Composable
fun DeleteButton(onDelete: () -> Unit) {
    var showingConfirmationDlg by remember { mutableStateOf(false) }

    IconButton(onClick = { showingConfirmationDlg = true }) {
        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
    }

    if (showingConfirmationDlg) {
        AlertDialog(
            title = { Text("Confirmation") },
            text = { Text("Are you sure want to delete the item?") },
            onDismissRequest = { showingConfirmationDlg = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showingConfirmationDlg = false
                    }
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showingConfirmationDlg = false }) { Text("Cancel") }
            }
        )
    }
}