package com.oliveira.maia.app.presentation.view

import SalesModal
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        val isDialogVisible by homeViewModel.exibirModal.collectAsState()

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
                        LatestSalesContainer(viewModel, navController).Content()
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                        )
                        CustomFabButton(homeViewModel)
                    }
                }
            }
        }
        val openAlertDialog = remember { mutableStateOf(false) }

        if (isDialogVisible) {
            SalesModal(homeViewModel).AlertDialogWithForm(
                onDismissRequest = {
                    openAlertDialog.value = false
                },
                onConfirmation = {
                    openAlertDialog.value = false

                },
                dialogTitle = "Cadastrar nova venda",
            )
        }
    }
}

@Composable
fun CustomFabButton(homeViewModel: HomeViewModel) {
    ExtendedFloatingActionButton(
        text = { Text("Nova Venda") },
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Nova venda",
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
