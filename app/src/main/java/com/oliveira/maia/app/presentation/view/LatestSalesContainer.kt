package com.oliveira.maia.app.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oliveira.maia.app.CustomComponentActivity
import com.oliveira.maia.app.domain.model.SaleEntity
import com.oliveira.maia.app.presentation.viemModel.LatestSalesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LatestSalesContainer  @Inject constructor(
    private val viewModel: LatestSalesViewModel,
    private val navController: NavController
) : CustomComponentActivity() {

    @Composable
    fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SalesContent(viewModel, navController)

            BottomSalesInfo(viewModel)
        }
    }
}

@Composable
fun SalesContent(viewModel: LatestSalesViewModel, navController: NavController) {
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
            items(viewModel.lastThreeSales) { sale ->
                SalesItem(sale = sale)
            }
            item {
                SeeAllItem(
                    onSeeAllClick = {
                        navController.navigate("all_sales")
                    }
                )
            }
        }
    }
}

@Composable
fun BottomSalesInfo(viewModel: LatestSalesViewModel){
    var totalSales = remember { "" }
    LaunchedEffect(viewModel) {
        totalSales = viewModel.getTotalSales()
    }
    Box(Modifier.padding(start = 16.dp, bottom = 120.dp),
        contentAlignment = Alignment.BottomStart) {
        Row {
            Text(
                text = "Total Vendas ",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = " R$$totalSales",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun SalesItem(sale: SaleEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Data: ${sale.orderDate}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "See Details",
            modifier = Modifier
                .size(24.dp)
                .clickable { /* Adicione a lógica para o clique do ícone aqui */ }
        )
    }
}

@Composable
fun SeeAllItem(onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
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
