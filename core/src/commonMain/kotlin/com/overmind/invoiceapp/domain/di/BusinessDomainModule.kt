package com.overmind.invoiceapp.domain.di

import com.overmind.invoiceapp.domain.usecases.business.AddBusiness
import com.overmind.invoiceapp.domain.usecases.business.DeleteBusiness
import com.overmind.invoiceapp.domain.usecases.business.FetchAllBusiness
import com.overmind.invoiceapp.domain.usecases.business.ValidateBusiness
import org.koin.dsl.module

val businessDomainModule = module {
    factory { FetchAllBusiness(get()) }
    factory { AddBusiness(get(), get()) }
    factory { ValidateBusiness() }
    factory { DeleteBusiness(get()) }
}