package com.overmind.invoiceapp.domain.usecases.clients

import com.overmind.invoiceapp.domain.datasources.ClientsDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteClient : KoinComponent {

    private val clientsDataSource by inject<ClientsDataSource>()

    suspend operator fun invoke(id: Int) {
        clientsDataSource.deleteClient(id)
    }
}
