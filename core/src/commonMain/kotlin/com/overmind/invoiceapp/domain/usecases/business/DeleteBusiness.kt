package com.overmind.invoiceapp.domain.usecases.business

import com.overmind.invoiceapp.domain.datasources.BusinessDataSource

class DeleteBusiness(private val businessDataSource: BusinessDataSource) {

    suspend operator fun invoke(id: Int) {
        businessDataSource.deleteBusiness(id)
    }
}
