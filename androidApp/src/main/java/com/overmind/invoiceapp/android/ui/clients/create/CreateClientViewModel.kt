package com.overmind.invoiceapp.android.ui.clients.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overmind.invoiceapp.domain.entities.Client
import com.overmind.invoiceapp.domain.usecases.AddClient
import com.overmind.invoiceapp.domain.usecases.ValidateClient
import kotlinx.coroutines.launch

class CreateClientViewModel(
    private val addClient: AddClient,
    private val validateClient: ValidateClient
) : ViewModel() {

    fun createClient(client: Client): ValidateClient.Result {
        return when(val result = validateClient(client)) {
            ValidateClient.Result.Ok -> {
                viewModelScope.launch { addClient(client) }
                result
            }
            else -> result
        }
    }
}