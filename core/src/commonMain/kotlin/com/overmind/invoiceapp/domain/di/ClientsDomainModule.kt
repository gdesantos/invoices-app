package com.overmind.invoiceapp.domain.di

import com.overmind.invoiceapp.domain.usecases.clients.*
import org.koin.dsl.module

val clientsDomainModule = module {
    factory { FetchClients(get()) }
    factory { FetchClient(get()) }
    factory { AddClient(get(), get()) }
    factory { DeleteClient(get()) }
    factory { ValidateClient() }
    factory { ModifyClient(get(), get()) }
    factory { FilterClient() }
}