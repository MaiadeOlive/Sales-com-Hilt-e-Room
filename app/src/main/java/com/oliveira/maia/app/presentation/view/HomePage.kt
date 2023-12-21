package com.oliveira.maia.app.presentation.view

import com.oliveira.maia.app.presentation.view.components.SalesModal
import android.app.Application
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oliveira.maia.app.CustomComponentActivity
import com.oliveira.maia.app.presentation.ui.theme.Black
import com.oliveira.maia.app.presentation.ui.theme.OmieTheme
import com.oliveira.maia.app.presentation.ui.theme.TiffanyPrimary
import com.oliveira.maia.app.presentation.viemModel.HomeViewModel
import com.oliveira.maia.app.presentation.viemModel.LatestSalesViewModel
import com.oliveira.maia.app.presentation.view.components.TopAppBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class HomePage @Inject constructor(
    private val navController: NavController
) : CustomComponentActivity() {

    @Composable
    fun Content(
        viewModel: LatestSalesViewModel = hiltViewModel(),
        homeViewModel: HomeViewModel = hiltViewModel(),
    ) {
        var updateScreen by remember { mutableStateOf(false) }
        val isDialogVisible by homeViewModel.exibirModal.collectAsState()
        val openAlertDialog = remember { mutableStateOf(false) }

        LaunchedEffect(homeViewModel.sales) {
            homeViewModel.sales.observe(this@HomePage) {
                updateScreen = true
            }
        }

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
                        LatestSalesContainer(viewModel, navController)
                        TotalSalesContainer(viewModel)
                        CustomFabButton(homeViewModel)


                        if (isDialogVisible) {
                            SalesModal(homeViewModel).AlertDialogWithForm(
                                onDismissRequest = {
                                    openAlertDialog.value = false
                                },
                                onConfirmation = {
                                    openAlertDialog.value = false
                                },
                                dialogTitle = "Cadastrar novo produto",
                            )
                        }

                        LaunchedEffect(homeViewModel.exibirToast) {
                            homeViewModel.exibirToast.collect { exibirToast ->
                                if (exibirToast) {
                                    showToast(application, "Preencha todos os campos")
                                    homeViewModel.hideToast()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TotalSalesContainer(viewModel: LatestSalesViewModel) {
        var totalSales by remember { mutableStateOf("") }

        LaunchedEffect(viewModel) {
            totalSales = viewModel.getTotalSales()
        }

        Box(
            Modifier
                .padding(start = 16.dp, bottom = 80.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
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
}


@Composable
fun CustomFabButton(homeViewModel: HomeViewModel) {
    ExtendedFloatingActionButton(
        text = { Text("Nova venda") },
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "nova venda",
                tint = Black
            )
        },
        onClick = {
            homeViewModel.showModal()
        },
        modifier = Modifier
            .padding(16.dp),
        contentColor = Black,
        containerColor = TiffanyPrimary,
    )
}

private fun showToast(application: Application, message: String) {
    Toast.makeText(application, message, Toast.LENGTH_LONG).show()
}

