package com.overmind.invoiceapp.android.ui.business.create

import androidx.lifecycle.ViewModel
import com.overmind.invoiceapp.domain.entities.Business
import com.overmind.invoiceapp.domain.usecases.business.AddBusiness
import kotlinx.coroutines.runBlocking

class CreateBusinessViewModel(private val addBusiness: AddBusiness) : ViewModel() {

    fun createBusiness(business: Business): AddBusiness.Result {
        return runBlocking { addBusiness(business) }
    }
}
