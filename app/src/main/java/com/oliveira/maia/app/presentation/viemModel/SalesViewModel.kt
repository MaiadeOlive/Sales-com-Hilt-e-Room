package com.oliveira.maia.app.presentation.viemModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliveira.maia.app.core.data.model.SaleEntity
import com.oliveira.maia.app.core.domain.useCase.GetAllSalesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val getAllSalesUseCase: GetAllSalesUseCase
) : ViewModel() {

    private val _salesState = MutableStateFlow<SalesState>(SalesState.Loading)
    val salesState: StateFlow<SalesState> get() = _salesState
    private val allSales = mutableListOf<SaleEntity>()

    private val _retryEvent = MutableLiveData<Unit>()

    init {
        loadAllSales()
    }

    private fun loadAllSales() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val latestSales = getAllSalesUseCase.invoke()
                allSales.clear()
                allSales.addAll(latestSales)
                _salesState.value = SalesState.Success(allSales)

            } catch (e: Exception) {
                _salesState.value =
                    SalesState.Error(e.localizedMessage ?: "Erro desconhecido")
            }
        }
    }
    fun retry() {
        _retryEvent.postValue(Unit)
    }
    sealed class SalesState {
        object Loading : SalesState()
        data class Success(val sales: List<SaleEntity>) : SalesState()
        data class Error(val errorMessage: String) : SalesState()
    }
}