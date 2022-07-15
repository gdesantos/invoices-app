package com.overmind.invoiceapp.domain.datasources

import com.overmind.invoiceapp.domain.entities.Business
import kotlinx.coroutines.flow.Flow

interface BusinessDataSource {

    suspend fun getAllBusiness(): Flow<List<Business>>
    suspend fun addBusiness(business: Business)
    suspend fun deleteBusiness(id: Int)
    suspend fun getBusiness(id: Int): Flow<Business>
    suspend fun modifyBusiness(business: Business)
    suspend fun countBusiness(): Flow<Int>
}

