package com.overmind.invoiceapp.domain.datasources

import com.overmind.invoiceapp.domain.entities.Client
import kotlinx.coroutines.flow.Flow

interface ClientsDataSource {

    suspend fun getClients(): Flow<List<Client>>
    suspend fun addClient(client: Client)
    suspend fun deleteClient(id: Int)
    suspend fun getClient(id: Int): Flow<Client>
}