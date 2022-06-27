package com.overmind.invoiceapp.android.ui.clients.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overmind.invoiceapp.android.ui.clients.emptyClient
import com.overmind.invoiceapp.domain.usecases.FetchClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditClientViewModel(
    private val fetchClient: FetchClient
) : ViewModel() {

    private val _uiState: MutableStateFlow<EditClientUiState> =
        MutableStateFlow(EditClientUiState(emptyClient()))
    val uiState: StateFlow<EditClientUiState> = _uiState

    fun getClient(id: Int) {
        viewModelScope.launch {
            fetchClient(id).collect { client ->
                _uiState.value = EditClientUiState(client)
            }
        }
    }
}