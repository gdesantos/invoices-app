package com.overmind.core.db.di

import com.overmind.core.db.datasources.SqldClientsDataSource
import com.overmind.invoiceapp.domain.datasources.ClientsDataSource
import org.koin.dsl.module

val clientsDbModule = module {
    single<ClientsDataSource> { SqldClientsDataSource() }
}