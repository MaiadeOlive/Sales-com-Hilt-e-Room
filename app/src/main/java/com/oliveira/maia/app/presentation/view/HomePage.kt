package com.oliveira.maia.app.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.oliveira.maia.app.presentation.ui.theme.Black
import com.oliveira.maia.app.presentation.ui.theme.OmieTheme
import com.oliveira.maia.app.presentation.ui.theme.TiffanyPrimary
import com.oliveira.maia.app.presentation.viemModel.LatestSalesEvent
import com.oliveira.maia.app.presentation.viemModel.LatestSalesState
import com.oliveira.maia.app.presentation.viemModel.LatestSalesViewModel
import com.oliveira.maia.app.presentation.view.components.AlertDialogWithForm
import com.oliveira.maia.app.presentation.view.components.TopAppBar

@Composable
fun HomePage(
    navHostController: NavHostController,
    viewModel: LatestSalesViewModel = hiltViewModel()
){
    val state = viewModel.latestSalesState.collectAsState()
    HomePageContent(
        state = state.value,
        onEvent = viewModel::onEvent,
        onNavigateToSalesPage = { navHostController.navigate("all_sales") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageContent(
    state: LatestSalesState,
    onNavigateToSalesPage: () -> Unit,
    onEvent: (LatestSalesEvent) -> Unit
) {
    val openAlertDialog = remember { mutableStateOf(false) }

    OmieTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Scaffold(
                topBar = {
                    TopAppBar()
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    LatestSalesContainer(state, onNavigateToSalesPage)
                    TotalSalesContainer(state)
                    CustomFabButton {
                        openAlertDialog.value = openAlertDialog.value.not()
                    }
                    AnimatedVisibility(visible = openAlertDialog.value) {
                        AlertDialogWithForm(
                            onCreateProductList = {
                                onEvent.invoke(LatestSalesEvent.CreateProduct(it))
                            },
                            onDismissRequest = {
                                openAlertDialog.value = false
                            },
                            onConfirmation = {
                                onEvent.invoke(LatestSalesEvent.CreateSale(it))
                            },
                            dialogTitle = "Cadastrar novo produto",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TotalSalesContainer(state: LatestSalesState) {
    Box(
        Modifier
            .padding(start = 16.dp, bottom = 80.dp, end = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomStart
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Total Vendas",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = getTotalAmount(state),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

fun getTotalAmount(state: LatestSalesState): String {
    val totalAmount = state.sales.sumOf { it.totalAmount }
    val formatStringTotalAmount = String.format("%.2f", totalAmount)
    return "R$$formatStringTotalAmount"
}

@Composable
fun CustomFabButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text("Nova venda") },
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "nova venda",
                tint = Black
            )
        },
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp),
        contentColor = Black,
        containerColor = TiffanyPrimary,
    )
}
