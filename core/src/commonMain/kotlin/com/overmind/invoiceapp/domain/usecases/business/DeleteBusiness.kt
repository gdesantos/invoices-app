package com.overmind.invoiceapp.domain.usecases.business

import com.overmind.invoiceapp.domain.datasources.BusinessDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteBusiness : KoinComponent {

    private val businessDataSource by inject<BusinessDataSource>()

    suspend operator fun invoke(id: Int) {
        businessDataSource.deleteBusiness(id)
    }
}
