package com.overmind.invoiceapp.domain.usecases.clients

import com.overmind.invoiceapp.domain.datasources.ClientsDataSource

class DeleteClient(private val clientsDataSource: ClientsDataSource) {

    suspend operator fun invoke(id: Int) {
        clientsDataSource.deleteClient(id)
    }
}
