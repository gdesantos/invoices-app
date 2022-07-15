package com.overmind.invoiceapp.domain.di

import com.overmind.invoiceapp.domain.usecases.clients.*
import org.koin.dsl.module

val clientsDomainModule = module {
    factory { FetchClients() }
    factory { FetchClient() }
    factory { AddClient() }
    factory { DeleteClient() }
    factory { ValidateClient() }
    factory { ModifyClient() }
    factory { FilterClient() }
}