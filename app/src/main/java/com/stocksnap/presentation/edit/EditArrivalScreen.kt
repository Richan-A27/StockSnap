package com.stocksnap.presentation.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stocksnap.domain.model.ProductStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditArrivalScreen(
    onBack: () -> Unit = {},
    viewModel: EditArrivalViewModel = hiltViewModel()
) {
    val arrivalState by viewModel.arrival.collectAsState()
    val arrival = arrivalState ?: return

    var mrp by remember { mutableStateOf(arrival.mrp.toString()) }
    var quantity by remember { mutableStateOf(arrival.quantityReceived.toString()) }
    var status by remember { mutableStateOf(arrival.status) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .verticalScroll(rememberScrollState())
    ) {
        // Toolbar
        TopAppBar(
            title = { Text("Edit Arrival Details", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = arrival.productName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006E3E)
                    )
                    Text(
                        text = "Barcode: ${arrival.barcode}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // MRP
                    OutlinedTextField(
                        value = mrp,
                        onValueChange = { mrp = it },
                        label = { Text("MRP (₹)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Quantity
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it.filter(Char::isDigit) },
                        label = { Text("Quantity Received") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Status Switcher
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Status: ${status.name}", fontWeight = FontWeight.Bold)
                        Switch(
                            checked = status == ProductStatus.UPDATED,
                            onCheckedChange = { isChecked ->
                                status = if (isChecked) ProductStatus.UPDATED else ProductStatus.PENDING
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF006E3E))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save Action
            Button(
                onClick = {
                    viewModel.saveArrival(
                        mrp = mrp.toDoubleOrNull() ?: arrival.mrp,
                        quantity = quantity.toIntOrNull() ?: arrival.quantityReceived,
                        status = status,
                        onSaved = onBack
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E))
            ) {
                Text("Save Delivery Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
