package com.oliveira.maia.app.presentation.viemModel

import android.app.Application
import android.database.sqlite.SQLiteException
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliveira.maia.app.domain.model.SaleEntity
import com.oliveira.maia.app.domain.useCase.CreateNewSaleUseCase
import com.oliveira.maia.app.domain.useCase.GetAllSalesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllSalesUseCase: GetAllSalesUseCase,
    private val createNewSaleUseCase: CreateNewSaleUseCase,
    private val application: Application
) : ViewModel() {

    private val _sales = MutableLiveData<List<SaleEntity>?>()
    val sales: LiveData<List<SaleEntity>?> get() = _sales

    private val _exibirModal = MutableStateFlow(false)
    val exibirModal: StateFlow<Boolean> get() = _exibirModal

    fun showModal() {
        _exibirModal.value = true
    }

    fun hideModal() {
        _exibirModal.value = false
    }

    fun createSale(sale: SaleEntity) {
        viewModelScope.launch {
            try {
                createNewSaleUseCase(sale)
                _sales.value = getAllSalesUseCase()
            } catch (sqliteException: SQLiteException) {
                showToast(application, "Erro ao acessar o banco de dados. Tente novamente mais tarde.")
            } catch (e: Exception) {
                showToast(application, "Erro ao acessar o banco de dados. Tente novamente mais tarde.")
            }
        }
    }

    private fun showToast(application: Application, message: String) {
        Toast.makeText(application, message, Toast.LENGTH_LONG).show()
    }
}