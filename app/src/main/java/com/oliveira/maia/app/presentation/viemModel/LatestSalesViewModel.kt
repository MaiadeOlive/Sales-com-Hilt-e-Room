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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LatestSalesViewModel @Inject constructor(
    private val getAllSalesUseCase: GetAllSalesUseCase
) : ViewModel() {

    private val _latestSalesState = MutableStateFlow<LatestSalesState>(LatestSalesState.Loading)
    val latestSalesState: StateFlow<LatestSalesState> get() = _latestSalesState

    private val lastThreeSales = mutableListOf<SaleEntity>()

    private val _retryEvent = MutableLiveData<Unit>()

    init {
        loadLatestSales()
    }

    private fun loadLatestSales() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val latestSales = getAllSalesUseCase.invoke()
                lastThreeSales.clear()

                var lastThreeSalesCount = latestSales.take(3).toMutableList()

                lastThreeSales.addAll(lastThreeSalesCount)
                _latestSalesState.value = LatestSalesState.Success(lastThreeSales)

            } catch (e: Exception) {
                _latestSalesState.value =
                    LatestSalesState.Error(e.localizedMessage ?: "Erro desconhecido")
            }
        }
    }

    fun retry() {
        _retryEvent.postValue(Unit)
    }

    suspend fun getTotalSales(): String {
        return withContext(Dispatchers.IO) {
            var totalSales = 0.0
            val sales = getAllSalesUseCase.invoke()
            sales.forEach {
                totalSales += it.totalAmount
            }
            String.format("%.2f", totalSales)
        }
    }

    sealed class LatestSalesState {
        object Loading : LatestSalesState()
        data class Success(val sales: List<SaleEntity>) : LatestSalesState()
        data class Error(val errorMessage: String) : LatestSalesState()
    }
}