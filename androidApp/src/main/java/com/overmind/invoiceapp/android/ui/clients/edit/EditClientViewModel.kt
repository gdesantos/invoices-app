package com.overmind.invoiceapp.android.ui.clients.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overmind.invoiceapp.android.ui.clients.emptyClient
import com.overmind.invoiceapp.domain.entities.Client
import com.overmind.invoiceapp.domain.usecases.clients.FetchClient
import com.overmind.invoiceapp.domain.usecases.clients.ModifyClient
import com.overmind.invoiceapp.domain.usecases.clients.ValidateClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EditClientViewModel(
    private val fetchClient: FetchClient,
    private val modifyClient: ModifyClient
) : ViewModel() {

    private val _uiState: MutableStateFlow<EditClientUiState> =
        MutableStateFlow(EditClientUiState(emptyClient()))
    val uiState: StateFlow<EditClientUiState> = _uiState

    fun getClient(id: Int) {
        viewModelScope.launch {
            fetchClient(id).first().also { client ->
                _uiState.value = EditClientUiState(client)
            }
        }
    }

    fun modifyClient(client: Client): ValidateClient.Result {
        return runBlocking {
            modifyClient.invoke(client)
        }
    }
}