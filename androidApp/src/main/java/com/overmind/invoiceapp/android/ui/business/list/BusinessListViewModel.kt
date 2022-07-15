package com.overmind.invoiceapp.android.ui.business.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overmind.invoiceapp.domain.usecases.business.DeleteBusiness
import com.overmind.invoiceapp.domain.usecases.business.FetchAllBusiness
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BusinessListViewModel(
    private val fetchAllBusiness: FetchAllBusiness,
    private val deleteBusiness: DeleteBusiness
) : ViewModel() {

    private val _uiState: MutableStateFlow<BusinessListUiState> =
        MutableStateFlow(BusinessListUiState(emptyList()))
    val uiState: StateFlow<BusinessListUiState> = _uiState

    init {
        viewModelScope.launch {
            fetchAllBusiness().collect { businessList ->
                _uiState.value = BusinessListUiState(businessList)
            }
        }
    }

    fun deleteBusiness(id: Int) {
        viewModelScope.launch { deleteBusiness.invoke(id) }
    }
}
