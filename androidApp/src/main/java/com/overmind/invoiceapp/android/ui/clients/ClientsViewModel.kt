package com.overmind.invoiceapp.android.ui.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overmind.invoiceapp.domain.entities.Client
import com.overmind.invoiceapp.domain.usecases.AddClient
import com.overmind.invoiceapp.domain.usecases.DeleteClient
import com.overmind.invoiceapp.domain.usecases.FetchClients
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClientsViewModel(
    private val fetchClients: FetchClients,
    private val deleteClient: DeleteClient
) : ViewModel() {

    private val _uiState: MutableStateFlow<ClientsUiState> =
        MutableStateFlow(ClientsUiState(emptyList()))
    val uiState: StateFlow<ClientsUiState> = _uiState

    init {
        viewModelScope.launch { fetchClients().collect { _uiState.value = ClientsUiState(it) } }
    }

    fun deleteClient(id: Int) {
        viewModelScope.launch { deleteClient.invoke(id) }
    }
}
