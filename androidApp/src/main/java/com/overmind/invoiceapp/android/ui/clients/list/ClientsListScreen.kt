package com.overmind.invoiceapp.android.ui.clients.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.RequestPage
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.overmind.invoiceapp.android.ui.common.DeleteButton
import com.overmind.invoiceapp.android.ui.common.EditButton
import com.overmind.invoiceapp.android.ui.common.ItemCard
import com.overmind.invoiceapp.android.ui.common.ItemCardField
import com.overmind.invoiceapp.android.ui.main.Screen
import com.overmind.invoiceapp.domain.entities.Client
import org.koin.androidx.compose.getViewModel

@Composable
fun ClientsListScreen(
    navController: NavController,
    viewModel: ClientsListViewModel = getViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            SearchBar { filter -> viewModel.filterClients(filter) }
            ClientsList(
                clients = uiState.filtered,
                navController = navController,
                viewModel = viewModel
            )
        }

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            onClick = { navController.navigate(Screen.CreateClient.route) }
        ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add client") }
    }
}

@Composable
private fun ClientsList(
    clients: List<Client>,
    navController: NavController,
    viewModel: ClientsListViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(clients) { client ->
            ClientItem(
                client = client,
                onDelete = { viewModel.deleteClient(client.id) },
                onEdit = { navController.navigate(Screen.EditClient.forClient(client.id)) }
            )
        }
    }
}

@Composable
private fun ClientItem(
    client: Client,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    ItemCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                modifier = Modifier.padding(bottom = 6.dp),
                text = client.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            ItemCardField(icon = Icons.Outlined.RequestPage, text = client.vat)
            ItemCardField(icon = Icons.Outlined.HomeWork, text = client.addressLine1)
            if (client.addressLine2.isNotEmpty()) {
                ItemCardField(icon = null, text = client.addressLine2)
            }
            if (client.phone.isNotEmpty()) {
                ItemCardField(icon = Icons.Outlined.Phone, text = client.phone)
            }
            if (client.email.isNotEmpty()) {
                ItemCardField(icon = Icons.Outlined.Email, text = client.email)
            }
        }
        Column(modifier = Modifier.align(Alignment.CenterEnd)) {
            DeleteButton(onDelete)
            EditButton { onEdit() }
        }
    }
}

@Composable
private fun SearchBar(onFilterChanged: (String) -> Unit) {
    var searchFilter by remember { mutableStateOf("") }

    TopAppBar(contentColor = Color.White) {
        TextField(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.padding(15.dp).size(24.dp)
                )
            },
            trailingIcon = {
                if (searchFilter.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            searchFilter = ""
                            onFilterChanged(searchFilter)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            modifier = Modifier.padding(15.dp).size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            placeholder = { Text("Search filter") },
            value = searchFilter,
            onValueChange = {
                searchFilter = it
                onFilterChanged(searchFilter)
            },
            colors =
                TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    leadingIconColor = Color.White,
                    trailingIconColor = Color.White,
                    placeholderColor = Color.White.copy(alpha = 0.4f),
                    backgroundColor = MaterialTheme.colors.primary
                )
        )
    }
}
