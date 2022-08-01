package com.overmind.invoiceapp.domain.usecases.clients

import com.overmind.invoiceapp.domain.datasources.ClientsDataSource
import com.overmind.invoiceapp.domain.entities.Client
import kotlinx.coroutines.flow.Flow

class FetchClient(private val clientsDataSource: ClientsDataSource) {

    suspend operator fun invoke(id: Int): Flow<Client> = clientsDataSource.getClient(id)
}
