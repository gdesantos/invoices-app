package com.overmind.invoiceapp.android.ui.business.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.RequestPage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.overmind.invoiceapp.android.ui.common.DeleteButton
import com.overmind.invoiceapp.android.ui.common.EditButton
import com.overmind.invoiceapp.android.ui.common.ItemCard
import com.overmind.invoiceapp.android.ui.common.ItemCardField
import com.overmind.invoiceapp.android.ui.main.Screen
import com.overmind.invoiceapp.domain.entities.Business
import org.koin.androidx.compose.getViewModel

@Composable
fun BusinessListScreen(
    navController: NavController,
    viewModel: BusinessListViewModel = getViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        BusinessList(
            businessList = uiState.businessList,
            navController = navController,
            viewModel = viewModel
        )

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            onClick = { navController.navigate(Screen.CreateBusiness.route) }
        ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add business") }
    }
}

@Composable
private fun BusinessList(
    businessList: List<Business>,
    navController: NavController,
    viewModel: BusinessListViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(businessList) { business ->
            BusinessItem(
                business = business,
                onDelete = { viewModel.deleteBusiness(business.id) },
                onEdit = { navController.navigate(Screen.EditBusiness.forBusiness(business.id)) }
            )
        }
    }
}

@Composable
private fun BusinessItem(
    business: Business,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    ItemCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                modifier = Modifier.padding(bottom = 6.dp),
                text = business.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            ItemCardField(icon = Icons.Outlined.RequestPage, text = business.vat)
            ItemCardField(icon = Icons.Outlined.HomeWork, text = business.addressLine1)
            if (business.addressLine2.isNotEmpty()) {
                ItemCardField(icon = null, text = business.addressLine2)
            }
            if (business.phone.isNotEmpty()) {
                ItemCardField(icon = Icons.Outlined.Phone, text = business.phone)
            }
            if (business.email.isNotEmpty()) {
                ItemCardField(icon = Icons.Outlined.Email, text = business.email)
            }
        }
        Column(modifier = Modifier.align(Alignment.CenterEnd)) {
            DeleteButton(onDelete)
            EditButton { onEdit() }
        }
    }
}
