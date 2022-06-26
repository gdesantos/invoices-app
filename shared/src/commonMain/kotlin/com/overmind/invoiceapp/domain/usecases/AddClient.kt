package com.overmind.invoiceapp.domain.usecases

import com.overmind.invoiceapp.domain.datasources.ClientsDataSource
import com.overmind.invoiceapp.domain.entities.Client
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddClient : KoinComponent {

    private val clientsDataSource by inject<ClientsDataSource>()

    suspend operator fun invoke(client: Client) {
        clientsDataSource.addClient(client)
    }
}