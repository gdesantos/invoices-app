package com.overmind.core.db.datasources

import com.overmind.invoiceapp.db.Clients
import com.overmind.invoiceapp.db.MyDatabase
import com.overmind.invoiceapp.domain.datasources.ClientsDataSource
import com.overmind.invoiceapp.domain.entities.Client
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SqldClientsDataSource : ClientsDataSource, KoinComponent {

    private val database: MyDatabase by inject()

    override suspend fun getClients(): Flow<List<Client>> =
        database.clientsQueries.selectAll().asFlow().mapToList().map {
            it.map { row -> row.toDomain() }
        }

    override suspend fun addClient(client: Client) {
        database.clientsQueries.insert(client.toDb())
    }

    override suspend fun deleteClient(id: Int) {
        database.clientsQueries.delete(id)
    }

    override suspend fun getClient(id: Int): Flow<Client> =
        database.clientsQueries.selectOne(id).asFlow().mapToOne().map { it.toDomain() }

    override suspend fun modifyClient(client: Client) {
        val clientDb = client.toDb()
        database.clientsQueries.update(
            name = clientDb.name,
            vat = clientDb.vat,
            address = clientDb.address,
            phone = clientDb.phone,
            email = clientDb.email,
            id = clientDb.id
        )
    }

    private fun Clients.toDomain() =
        Client(
            id = id,
            name = name,
            addressLine1 = address.lines().firstOrNull() ?: "",
            addressLine2 = address.lines().getOrNull(1) ?: "",
            vat = vat,
            phone = phone,
            email = email
        )

    private fun Client.toDb() =
        Clients(
            id = id,
            name = name,
            vat = vat,
            address = (addressLine1 + "\n" + addressLine2).trim(),
            phone = phone,
            email = email
        )
}
