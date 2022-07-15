package com.overmind.core.db.di

import com.overmind.core.db.datasources.SqldBusinessDataSource
import com.overmind.invoiceapp.domain.datasources.BusinessDataSource
import org.koin.dsl.module

val businessDbModule = module {
    single<BusinessDataSource> { SqldBusinessDataSource() }
}