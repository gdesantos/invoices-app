package com.overmind.invoiceapp.android.ui.clients.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.overmind.invoiceapp.android.ui.main.Screen
import com.overmind.invoiceapp.domain.entities.Client
import org.koin.androidx.compose.get

@Composable
fun ClientsScreen(navController: NavController, viewModel: ClientsListViewModel = get()) {

    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        ClientsList(uiState = uiState)
        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            onClick = { navController.navigate(Screen.CreateClient.route) }
        ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add client") }
    }
}

@Composable
private fun ClientsList(uiState: ClientsListUiState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) { items(uiState.clients) { client -> ClientItem(client = client) } }
}

@Composable
private fun ClientItem(client: Client) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
    ) {
        Box(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)) {
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 6.dp),
                    text = client.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                ClientField(icon = Icons.Outlined.RequestPage, text = client.vat)
                ClientField(icon = Icons.Outlined.HomeWork, text = client.addressLine1)
                if (client.addressLine2.isNotEmpty()) {
                    ClientField(icon = null, text = client.addressLine2)
                }
                ClientField(icon = Icons.Outlined.Phone, text = client.phone.toString())
                ClientField(icon = Icons.Outlined.Email, text = client.email)
            }
            Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                DeleteButton(client)
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit")
                }
            }
        }
    }
}

@Composable
private fun ClientField(icon: ImageVector?, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
        } else {
            Box(modifier = Modifier.size(24.dp))
        }
        Text(text = text, fontSize = 14.sp)
    }
}

@Composable
private fun DeleteButton(client: Client, viewModel: ClientsListViewModel = get()) {
    var showingConfirmationDlg by remember { mutableStateOf(false) }

    IconButton(onClick = { showingConfirmationDlg = true }) {
        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
    }

    if (showingConfirmationDlg) {
        AlertDialog(
            title = { Text("Confirmation") },
            text = { Text("Are you sure want to delete the client ${client.name} ?") },
            onDismissRequest = { showingConfirmationDlg = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteClient(client.id)
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

@Composable
@Preview
private fun ClientItemPreview() {
    ClientItem(
        client =
            Client(
                0,
                "Gustavo de Santos",
                "51451166M",
                "Los Madroños 38",
                "Alcorcón",
                658632081L,
                "gdesantos@gmail.com"
            )
    )
}
