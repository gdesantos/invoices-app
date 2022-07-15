package com.overmind.invoiceapp.android.ui.business.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.overmind.invoiceapp.android.ui.business.BusinessForm
import com.overmind.invoiceapp.android.ui.business.emptyBusiness
import com.overmind.invoiceapp.domain.usecases.business.AddBusiness
import org.koin.androidx.compose.get

@Composable
fun CreateBusinessScreen(navController: NavController, viewModel: CreateBusinessViewModel = get()) {
    val newBusiness = remember { mutableStateOf(emptyBusiness()) }
    val createResult = remember { mutableStateOf<AddBusiness.Result?>(null) }

    BusinessForm(
        title = "New business",
        business = newBusiness,
        result = createResult,
        onBack = {
            navController.navigateUp()
                 },
        onSave = { createResult.value = viewModel.createBusiness(newBusiness.value) }
    )
}