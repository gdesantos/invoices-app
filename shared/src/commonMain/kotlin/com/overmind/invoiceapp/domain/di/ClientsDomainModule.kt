package com.overmind.invoiceapp.domain.di

import com.overmind.invoiceapp.domain.usecases.AddClient
import com.overmind.invoiceapp.domain.usecases.DeleteClient
import com.overmind.invoiceapp.domain.usecases.FetchClients
import com.overmind.invoiceapp.domain.usecases.ValidateClient
import org.koin.dsl.module

val clientsDomainModule = module {
    factory { FetchClients() }
    factory { AddClient() }
    factory { DeleteClient() }
    factory { ValidateClient() }
}