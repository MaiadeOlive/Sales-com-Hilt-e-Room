package com.oliveira.maia.app.presentation.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.oliveira.maia.app.core.domain.model.ProductEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogWithForm(
    dialogTitle: String,
    onDismissRequest: () -> Unit,
    onConfirmation: (clientName: String) -> Unit,
    onCreateProductList: (ProductEntity) -> Unit,
) {
    var clientName by remember { mutableStateOf("") }
    var lockedClientName by remember { mutableStateOf(true) }
    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var unitPrice by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Nome do Cliente") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    enabled = lockedClientName
                )
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Nome do Produto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = productDescription,
                    onValueChange = { productDescription = it },
                    label = { Text("Descrição") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = unitPrice,
                    onValueChange = { unitPrice = it },
                    label = { Text("Preço Unitário") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantidade") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (
                        productName.isBlank() ||
                        productDescription.isBlank() ||
                        quantity.isBlank() ||
                        unitPrice.isBlank()
                    ) {
//                        showToastError()
                    } else {
                        onCreateProductList.invoke(
                            ProductEntity(
                                productId = 0,
                                productName = productName,
                                description = productDescription,
                                quantity = quantity.toInt(),
                                unitPrice = unitPrice.toDouble(),
                                totalAmount = unitPrice.toDouble() * quantity.toInt(),
                            )
                        )
                        productName = ""
                        productDescription = ""
                        quantity = ""
                        unitPrice = ""
                        lockedClientName = false
                    }
                }
            ) {
                Text("Incluir")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                TextButton(
                    onClick = {
                        onConfirmation(clientName)
                        onDismissRequest()
                    }

                ) {
                    Text("Salvar")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}

