package com.overmind.invoiceapp.android.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overmind.invoiceapp.domain.usecases.FetchClients
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val fetchClients: FetchClients,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> =
        MutableStateFlow(
            MainUiState(
                clientsTabEnabled = false,
                businessTabEnabled = false,
                productsTabEnabled = false,
                invoicesTabEnabled = false
            )
        )
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        viewModelScope.launch {
            fetchClients().collect {
                _uiState.value =
                    MainUiState(
                        clientsTabEnabled = true,
                        businessTabEnabled = it.isNotEmpty(),
                        productsTabEnabled = false,
                        invoicesTabEnabled = false
                    )
            }
        }
    }
}
