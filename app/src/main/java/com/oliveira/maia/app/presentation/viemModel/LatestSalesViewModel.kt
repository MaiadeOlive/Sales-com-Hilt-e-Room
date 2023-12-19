package com.oliveira.maia.app.presentation.viemModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliveira.maia.app.domain.model.SaleEntity
import com.oliveira.maia.app.domain.useCase.GetAllSalesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class LatestSalesViewModel @Inject constructor(
    private val getAllSalesUseCase: GetAllSalesUseCase
) : ViewModel() {

    private val _latestSalesState = MutableStateFlow<LatestSalesState>(LatestSalesState.Loading)
    val latestSalesState: StateFlow<LatestSalesState> get() = _latestSalesState
    val lastThreeSales = mutableListOf<SaleEntity>()

    init {
        viewModelScope.launch {
            try {
                delay(1000)
                val latestSales = getLatestSales()
                _latestSalesState.value = LatestSalesState.Success(latestSales)
                lastThreeSales.addAll(latestSales.takeLast(3))
            } catch (e: Exception) {
                _latestSalesState.value = LatestSalesState.Error(e.localizedMessage ?: "Erro desconhecido")
            }
        }
    }

    private fun getLatestSales(): List<SaleEntity> {
        return getAllSalesUseCase.invoke()
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