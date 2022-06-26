package com.overmind.invoiceapp.android.di

import com.overmind.invoiceapp.android.ui.clients.ClientsViewModel
import com.overmind.invoiceapp.android.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ClientsViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
}
