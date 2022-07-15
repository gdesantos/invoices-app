package com.overmind.core.db.datasources

import com.overmind.invoiceapp.db.Business as BusinessDb
import com.overmind.invoiceapp.db.MyDatabase
import com.overmind.invoiceapp.domain.datasources.BusinessDataSource
import com.overmind.invoiceapp.domain.entities.Business
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SqldBusinessDataSource : BusinessDataSource, KoinComponent {

    private val database: MyDatabase by inject()

    override suspend fun getAllBusiness(): Flow<List<Business>> =
        database.businessQueries.selectAll().asFlow().mapToList().map {
            it.map { row -> row.toDomain() }
        }

    override suspend fun addBusiness(business: Business) {
        database.businessQueries.insert(business.toDb())
    }

    override suspend fun deleteBusiness(id: Int) {
        database.businessQueries.delete(id)
    }

    override suspend fun getBusiness(id: Int): Flow<Business> =
        database.businessQueries.selectOne(id).asFlow().mapToOne().map { it.toDomain() }

    override suspend fun modifyBusiness(business: Business) {
        val businessDb = business.toDb()
        database.businessQueries.update(
            name = businessDb.name,
            vat = businessDb.vat,
            address = businessDb.address,
            phone = businessDb.phone,
            email = businessDb.email,
            logo = businessDb.logo,
            id = businessDb.id
        )
    }

    override suspend fun countBusiness(): Flow<Int> =
        database.businessQueries.countBusiness().asFlow().mapToOne().map { it.toInt() }

    private fun BusinessDb.toDomain() =
        Business(
            id = id,
            name = name,
            addressLine1 = address.lines().firstOrNull() ?: "",
            addressLine2 = address.lines().getOrNull(1) ?: "",
            vat = vat,
            phone = phone,
            email = email,
            logo = logo
        )

    private fun Business.toDb() =
        BusinessDb(
            id = id,
            name = name,
            vat = vat,
            address = (addressLine1 + "\n" + addressLine2).trim(),
            phone = phone,
            email = email,
            logo = logo
        )
}
