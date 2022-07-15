package com.overmind.invoiceapp.domain.usecases.clients

import com.overmind.invoiceapp.domain.datasources.ClientsDataSource
import com.overmind.invoiceapp.domain.entities.Client
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ModifyClient : KoinComponent {

    private val clientsDataSource by inject<ClientsDataSource>()
    private val validateClient by inject<ValidateClient>()

    suspend operator fun invoke(client: Client): ValidateClient.Result {
        return when(val result = validateClient(client)) {
            ValidateClient.Result.Ok -> {
                clientsDataSource.modifyClient(client)
                result
            }
            else -> result
        }
    }
}