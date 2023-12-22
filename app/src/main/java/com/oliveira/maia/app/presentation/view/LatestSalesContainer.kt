package com.oliveira.maia.app.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oliveira.maia.app.core.domain.model.ProductEntity
import com.oliveira.maia.app.core.domain.model.SaleEntity
import com.oliveira.maia.app.presentation.viemModel.LatestSalesState

@Composable
fun LatestSalesContainer(
    salesState: LatestSalesState,
    onNavigateToSalesPage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Últimas Vendas",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn {
            when {
                salesState.sales.isNotEmpty() -> {
                    items(salesState.sales.take(3)) { sale ->
                        SaleCardItem(sale = sale)
                    }
                }
                salesState.isLoading -> {
                    item {
                        LoadingIndicator()
                    }
                }
                salesState.sales.isEmpty() -> {
                    item {
                        ErrorView(errorMessage = "Não há vendas cadastradas")
                    }
                }
            }
            item {
                SeeAllItem {
                    onNavigateToSalesPage()
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(errorMessage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Erro: $errorMessage",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun SaleCardItem(sale: SaleEntity) {
    Card(
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
                Text(text = "N. Venda: ${sale.saleId}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Data: ${sale.orderDate}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Cliente: ${sale.clientName}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Valor Total: ${sale.totalAmount}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun SaleProductItem(product: ProductEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(
            text = "Nome Produto: ${product.productName}",
            style = MaterialTheme.typography.titleSmall
        )
        Text(text = "Descricao: ${product.description}", style = MaterialTheme.typography.bodySmall)
        Text(
            text = "Qnt de produtos: ${product.quantity}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Valor unitario: ${product.unitPrice}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun SeeAllItem(onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onSeeAllClick() }
    ) {
        Text(
            text = "Ver Mais",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Ver mais",
            modifier = Modifier
                .size(24.dp)
        )
    }
}
