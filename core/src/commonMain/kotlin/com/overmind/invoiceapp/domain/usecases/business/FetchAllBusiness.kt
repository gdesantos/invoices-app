package com.overmind.invoiceapp.domain.usecases.business

import com.overmind.invoiceapp.domain.datasources.BusinessDataSource
import com.overmind.invoiceapp.domain.entities.Business
import kotlinx.coroutines.flow.Flow

class FetchAllBusiness(private val businessDataSource: BusinessDataSource) {

    suspend operator fun invoke(): Flow<List<Business>> = businessDataSource.getAllBusiness()
}
