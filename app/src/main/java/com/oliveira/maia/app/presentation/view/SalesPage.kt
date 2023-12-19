package com.oliveira.maia.app.presentation.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.oliveira.maia.app.CustomComponentActivity
import com.oliveira.maia.app.domain.model.SaleEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SalesPage : CustomComponentActivity(){

    @Inject
    lateinit var sales: List<SaleEntity>

    @Composable
    fun SalesScreen() {
        var selectedSale by remember { mutableStateOf<SaleEntity?>(null) }
        val totalSales = totalSalesAmount(sales)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SalesList(sales) {
                selectedSale = it
            }
            BottomBar(totalSales)
            selectedSale?.let { sale ->
                SaleDetailsModal(sale) {
                    selectedSale = null
                }
            }
        }
    }

    private fun totalSalesAmount(sales: List<SaleEntity>): Double {
        var totalSales = 0.0
        sales.forEach {
            totalSales += it.unitPrice * it.quantity
        }
        return totalSales
    }

    @Composable
    fun SalesList(sales: List<SaleEntity>, onVendaClick: (SaleEntity) -> Unit) {
        LazyColumn {
            items(sales) { venda ->
                SaleCardItem(venda) {
                    onVendaClick(it)
                }
            }
        }
    }

    @Composable
    fun SaleCardItem(sale: SaleEntity, onClick: (SaleEntity) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(sale) }
                .padding(8.dp),
            elevation = 8.dp
        ) {
            // ... (seu conteúdo existente)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun SaleDetailsModal(sale: SaleEntity, onClose: () -> Unit) {
        val modalState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

        ModalBottomSheetLayout(
            sheetState = modalState,
            sheetContent = {
                // Conteúdo do modal com os detalhes da venda
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = sale.description,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(text = "Data: ${sale.orderDate}")
                    Text(text = "Valor: R$ ${sale.unitPrice}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Valor Total: R$ ${sale.totalAmount}")
                }
            },
            content = {
                // Conteúdo principal da tela
            }
        )

        LaunchedEffect(key1 = modalState) {
            modalState.show()
        }

        DisposableEffect(Unit) {
            onDispose {
                onClose()
            }
        }
    }

    @Composable
    fun BottomBar(totalVendas: Double) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = Color.Gray)
        ) {
            Text(
                text = "Total: $totalVendas",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                color = Color.White
            )
        }
    }
}