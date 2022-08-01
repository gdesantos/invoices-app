package com.overmind.invoiceapp.domain.usecases.clients

import com.overmind.invoiceapp.domain.datasources.ClientsDataSource
import com.overmind.invoiceapp.domain.entities.Client

class ModifyClient(
    private val clientsDataSource: ClientsDataSource,
    private val validateClient: ValidateClient
) {

    suspend operator fun invoke(client: Client): ValidateClient.Result {
        return when (val result = validateClient(client)) {
            ValidateClient.Result.Ok -> {
                clientsDataSource.modifyClient(client)
                result
            }
            else -> result
        }
    }
}
