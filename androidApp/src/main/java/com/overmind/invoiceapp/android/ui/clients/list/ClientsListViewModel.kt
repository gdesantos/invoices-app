package com.overmind.invoiceapp.android.ui.clients.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overmind.invoiceapp.domain.usecases.DeleteClient
import com.overmind.invoiceapp.domain.usecases.FetchClients
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClientsListViewModel(
    private val fetchClients: FetchClients,
    private val deleteClient: DeleteClient
) : ViewModel() {

    private val _uiState: MutableStateFlow<ClientsListUiState> =
        MutableStateFlow(ClientsListUiState(emptyList()))
    val uiState: StateFlow<ClientsListUiState> = _uiState

    init {
        viewModelScope.launch { fetchClients().collect { _uiState.value = ClientsListUiState(it) } }
    }

    fun deleteClient(id: Int) {
        viewModelScope.launch { deleteClient.invoke(id) }
    }
}
