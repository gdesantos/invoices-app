package com.overmind.invoiceapp.domain.usecases

import com.overmind.invoiceapp.domain.datasources.ClientsDataSource
import com.overmind.invoiceapp.domain.entities.Client
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchClient : KoinComponent {

    private val clientsDataSource by inject<ClientsDataSource>()

    suspend operator fun invoke(id: Int): Flow<Client> = clientsDataSource.getClient(id)
}