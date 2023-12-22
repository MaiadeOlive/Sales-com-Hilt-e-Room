package com.oliveira.maia.app.presentation.viemModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliveira.maia.app.core.domain.model.ProductEntity
import com.oliveira.maia.app.core.domain.model.SaleEntity
import com.oliveira.maia.app.core.domain.useCase.CreateNewSaleUseCase
import com.oliveira.maia.app.core.domain.useCase.GetAllSalesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

data class LatestSalesState(
    val sales: List<SaleEntity> = listOf(),
    val isLoading: Boolean = false
)

sealed class LatestSalesEvent {
    data class CreateProduct(val product: ProductEntity) : LatestSalesEvent()
    data class CreateSale(val clientName: String) : LatestSalesEvent()
}

@HiltViewModel
class LatestSalesViewModel @Inject constructor(
    private val getAllSalesUseCase: GetAllSalesUseCase,
    private val createNewSaleUseCase: CreateNewSaleUseCase,
) : ViewModel() {
    private val currentDateTime = Calendar.getInstance().time
    private val formatter = SimpleDateFormat("dd-MM-yyyy")
    private val formattedDateTime: String = formatter.format(currentDateTime).toString()

    private val _latestSalesState = MutableStateFlow(LatestSalesState())
    val latestSalesState: StateFlow<LatestSalesState> get() = _latestSalesState

    private var productList = mutableListOf<ProductEntity>()

    init {
        updateSaleList()
    }

    fun onEvent(event: LatestSalesEvent) {
        when (event) {
            is LatestSalesEvent.CreateProduct -> addProductToList(event.product)
            is LatestSalesEvent.CreateSale -> validateAndCreateSale(event.clientName)
        }
    }

    private fun updateSaleList() {
        _latestSalesState.update {
            return@update it.copy(sales = getAllSalesUseCase.invoke())
        }
    }

    private fun addProductToList(product: ProductEntity) {
        productList.add(product)
    }

    private fun validateAndCreateSale(
        clientName: String,
    ) {
        if (productList.isEmpty()) {
            println("Por favor, preencha todos os campos.")
            return
        } else {
            val sale = SaleEntity(
                saleId = 0,
                clientName = clientName,
                sale = productList,
                orderDate = formattedDateTime,
                totalAmount = productList.sumOf { it.totalAmount },
            )
            viewModelScope.launch(Dispatchers.IO) {
                createNewSaleUseCase(sale)
                updateSaleList()
            }
        }
    }
}