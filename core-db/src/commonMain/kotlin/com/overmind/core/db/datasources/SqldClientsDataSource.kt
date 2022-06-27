package com.overmind.core.db.datasources

import com.overmind.invoiceapp.db.Clients
import com.overmind.invoiceapp.db.MyDatabase
import com.overmind.invoiceapp.domain.datasources.ClientsDataSource
import com.overmind.invoiceapp.domain.entities.Client
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SqldClientsDataSource : ClientsDataSource, KoinComponent {

    private val database: MyDatabase by inject()

    override suspend fun getClients(): Flow<List<Client>> =
        database.clientsQueries.selectAll().asFlow().mapToList().map {
            it.map { row ->
                row.toDomain()
            }
        }

    override suspend fun addClient(client: Client) {
        database.clientsQueries.insert(client.toDb())
    }

    override suspend fun deleteClient(id: Int) {
        database.clientsQueries.delete(id.toLong())
    }

    private fun Clients.toDomain() = Client(
        id = id.toInt(),
        name = name,
        addressLine1 = address.lines().firstOrNull() ?: "",
        addressLine2 = address.lines().getOrNull(1) ?: "",
        vat = vat,
        phone = phone,
        email = email
    )

    private fun Client.toDb() = Clients(
        id = 0L,
        name = name,
        vat = vat,
        address = (addressLine1 + "\n" + addressLine2).trim(),
        phone = phone,
        email = email
    )
}