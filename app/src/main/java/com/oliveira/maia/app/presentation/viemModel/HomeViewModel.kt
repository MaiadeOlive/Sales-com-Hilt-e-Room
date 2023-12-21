package com.oliveira.maia.app.presentation.viemModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliveira.maia.app.core.data.model.ProductEntity
import com.oliveira.maia.app.core.data.model.SaleEntity
import com.oliveira.maia.app.core.domain.useCase.CreateNewSaleUseCase
import com.oliveira.maia.app.core.domain.useCase.GetAllSalesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllSalesUseCase: GetAllSalesUseCase,
    private val createNewSaleUseCase: CreateNewSaleUseCase,
) : ViewModel() {
    private val currentDateTime = Calendar.getInstance().time
    private val formatter = SimpleDateFormat("dd-MM-yyyy")
    private val formattedDateTime: String = formatter.format(currentDateTime).toString()

    private val _sales = MutableLiveData<List<SaleEntity>?>()
    val sales: LiveData<List<SaleEntity>?> get() = _sales

    private val _exibirModal = MutableStateFlow(false)
    val exibirModal: StateFlow<Boolean> get() = _exibirModal

    private val _exibirToast = MutableStateFlow(false)
    val exibirToast: StateFlow<Boolean> get() = _exibirToast

    private val _lastSaleId = MutableStateFlow(0)
    val lastSaleId: StateFlow<Int> get() = _lastSaleId

    private var productList = mutableListOf<ProductEntity>()

    fun setLastSaleId(id: Int) {
        _lastSaleId.value = id
    }

    fun showModal() {
        _exibirModal.value = true
    }

    fun hideModal() {
        _exibirModal.value = false
    }

    private fun showToastError() {
        _exibirToast.value = true
    }

    fun hideToast() {
        _exibirToast.value = false
    }

    private fun createSale(sale: SaleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            createNewSaleUseCase(sale)
            _sales.postValue(getAllSalesUseCase())
        }
    }

    fun createProductList(
        productName: String,
        description: String,
        quantity: Int,
        unitPrice: Double,
    ) {
        if (productName.isBlank() || description.isBlank() || quantity == 0 || unitPrice == 0.0) {
            showToastError()
        } else {
            productList.add(
                ProductEntity(
                    productId = 0,
                    productName = productName,
                    description = description,
                    quantity = quantity,
                    unitPrice = unitPrice,
                    totalAmount = unitPrice * quantity,
                )
            )
        }
    }

    fun validateAndCreateSale(
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
            createSale(sale)
            setLastSaleId(sale.saleId)
            _sales.postValue(sales.value?.plus(sale))
            hideModal()
        }
    }
}