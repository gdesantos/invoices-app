package com.overmind.invoiceapp.android.ui.clients.create

import androidx.lifecycle.ViewModel
import com.overmind.invoiceapp.domain.entities.Client
import com.overmind.invoiceapp.domain.usecases.clients.AddClient
import com.overmind.invoiceapp.domain.usecases.clients.ValidateClient
import kotlinx.coroutines.runBlocking

class CreateClientViewModel(
    private val addClient: AddClient
) : ViewModel() {

    fun createClient(client: Client): ValidateClient.Result {
        return runBlocking {
            addClient(client)
        }
    }
}