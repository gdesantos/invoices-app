package com.overmind.invoiceapp.android.ui.clients.list

import com.overmind.invoiceapp.domain.entities.Client

data class ClientsListUiState(
    val clients: List<Client>,
    val filtered: List<Client>
)
