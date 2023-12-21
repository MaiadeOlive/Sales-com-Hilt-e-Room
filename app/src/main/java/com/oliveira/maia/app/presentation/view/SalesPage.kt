package com.oliveira.maia.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oliveira.maia.app.CustomComponentActivity
import com.oliveira.maia.app.core.data.model.SaleEntity
import com.oliveira.maia.app.presentation.ui.theme.OmieTheme
import com.oliveira.maia.app.presentation.viemModel.SalesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SalesPage @Inject constructor(
    private val navController: NavController
) : CustomComponentActivity() {
    @Composable
    fun Content(
        viewModel: SalesViewModel = hiltViewModel(),
    ) {
        OmieTheme {
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            modifier = Modifier.background(Color.White),
                            backgroundColor = Color.White,
                            title = { Text(text = "Histórico de vendas") },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        SalesPage(viewModel, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SalesPage(viewModel: SalesViewModel, navController: NavController) {
    val salesState by viewModel.salesState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        androidx.compose.material3.Text(
            text = "Todas as Vendas",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        SalesContentAllSales(salesState, viewModel, navController)
    }
}


@Composable
fun SalesContentAllSales(
    salesState: SalesViewModel.SalesState,
    viewModel: SalesViewModel,
    navController: NavController,
) {
    LazyColumn {
        when (salesState) {
            is SalesViewModel.SalesState.Success -> {
                items(salesState.sales) { sale ->
                    SaleCardItemAllInfo(sale, onClick = {
                        navController.navigate("saleDetails/${sale.saleId}")
                    })
                }
            }

            is SalesViewModel.SalesState.Loading -> {
                item {
                    LoadingIndicator()
                }
            }

            is SalesViewModel.SalesState.Error -> {
                item {
                    ErrorView(errorMessage = salesState.errorMessage) {
                        viewModel.retry()
                    }
                }
            }
        }
    }
}


@Composable
fun SaleCardItemAllInfo(sale: SaleEntity, onClick: (SaleEntity) -> Unit) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material3.Text(
                    text = "N. Venda: ${sale.saleId}",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
                androidx.compose.material3.Text(
                    text = "Data: ${sale.orderDate}",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cliente: ${sale.clientName}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                sale.sale.forEach { product ->
                    SaleProductItem(product)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material3.Text(
                    text = "Valor Total: ${sale.totalAmount}",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}