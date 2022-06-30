package com.overmind.invoiceapp.android.di

import com.overmind.invoiceapp.android.ui.clients.list.ClientsListViewModel
import com.overmind.invoiceapp.android.ui.clients.create.CreateClientViewModel
import com.overmind.invoiceapp.android.ui.clients.edit.EditClientViewModel
import com.overmind.invoiceapp.android.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ClientsListViewModel(get(), get(), get()) }
    viewModel { CreateClientViewModel(get()) }
    viewModel { EditClientViewModel(get(), get()) }
}
