package com.overmind.invoiceapp.domain.usecases.business

import com.overmind.invoiceapp.domain.datasources.BusinessDataSource
import com.overmind.invoiceapp.domain.entities.Business
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchAllBusiness : KoinComponent {

    private val businessDataSource by inject<BusinessDataSource>()

    suspend operator fun invoke(): Flow<List<Business>> = businessDataSource.getAllBusiness()
}
