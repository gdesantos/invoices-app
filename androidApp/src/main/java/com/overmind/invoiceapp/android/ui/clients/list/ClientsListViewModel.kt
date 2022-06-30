package com.overmind.invoiceapp.android.ui.clients.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overmind.invoiceapp.domain.usecases.DeleteClient
import com.overmind.invoiceapp.domain.usecases.FetchClients
import com.overmind.invoiceapp.domain.usecases.FilterClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClientsListViewModel(
    private val fetchClients: FetchClients,
    private val deleteClient: DeleteClient,
    private val filterClient: FilterClient
) : ViewModel() {

    private val _uiState: MutableStateFlow<ClientsListUiState> =
        MutableStateFlow(ClientsListUiState(emptyList(), emptyList()))
    val uiState: StateFlow<ClientsListUiState> = _uiState

    init {
        viewModelScope.launch {
            fetchClients().collect { clients ->
                _uiState.value = ClientsListUiState(clients = clients, filtered = clients)
            }
        }
    }

    fun deleteClient(id: Int) {
        viewModelScope.launch { deleteClient.invoke(id) }
    }

    fun filterClients(filter: String) {
        _uiState.value =
            _uiState.value.copy(
                filtered = _uiState.value.clients.filter { filterClient(it, filter) }
            )
    }
}
