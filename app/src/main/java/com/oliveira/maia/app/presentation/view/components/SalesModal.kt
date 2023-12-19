import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import com.oliveira.maia.app.domain.model.SaleEntity
import com.oliveira.maia.app.presentation.viemModel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class SalesModal @Inject constructor(
    private val viewModel: HomeViewModel
) {
    private var nextSaleId = 1L
    private val currentDateTime = Calendar.getInstance().time
    private val formatter = SimpleDateFormat("yyyy-MM-dd - HH:mm:ss")
    private val formattedDateTime: String = formatter.format(currentDateTime).toString()

    private fun validateAndCreateSale(
        productName: String,
        description: String,
        quantity: Int,
        unitPrice: Double,
        viewModel: HomeViewModel
    ) {
        if (productName.isBlank() || description.isBlank() || quantity == 0 || unitPrice == 0.0) {
            println("Por favor, preencha todos os campos.")
            return
        } else {
            val sale = SaleEntity(
                id = nextSaleId,
                productName = productName,
                description = description,
                quantity = quantity,
                unitPrice = unitPrice,
                orderDate = formattedDateTime,
                totalAmount = unitPrice * quantity,
            )
            viewModel.createSale(sale)
            viewModel.hideModal()
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddProductDialog() {
        var productName by remember { mutableStateOf("") }
        var productDescription by remember { mutableStateOf("") }
        var unitPrice by remember { mutableStateOf("") }
        var quantity by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                viewModel.hideModal()
            },
            confirmButton = {
                Button(
                    onClick = {
                        validateAndCreateSale(
                            productName = productName,
                            description = productDescription,
                            quantity = quantity.toInt(),
                            unitPrice = unitPrice.toDouble(),
                            viewModel = viewModel
                        )
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Confirmar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Confirmar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.hideModal()
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Cancelar")
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
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
                        value = unitPrice,
                        onValueChange = { unitPrice = it },
                        label = { Text("Preço Unitário") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Quantidade") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AlertDialogWithForm(
        dialogTitle: String,
        onDismissRequest: () -> Unit,
        onConfirmation: () -> Unit,
    ) {
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
                        value = unitPrice,
                        onValueChange = { unitPrice = it },
                        label = { Text("Preço Unitário") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
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
                        validateAndCreateSale(
                            productName = productName,
                            description = productDescription,
                            quantity = quantity.toInt(),
                            unitPrice = unitPrice.toDouble(),
                            viewModel = viewModel
                        )
                        onConfirmation()
                    }
                ) {
                    Text("Salvar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.hideModal()
                        onDismissRequest()
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
