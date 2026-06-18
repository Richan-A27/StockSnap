package com.stocksnap.presentation.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ReviewScreen(onSave: () -> Unit = {}, viewModel: ReviewViewModel) {
    val productState by viewModel.product.collectAsState()

    var name by remember { mutableStateOf("") }
    var barcode by remember { mutableStateOf("") }
    var mrp by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    val previousMrp by viewModel.previousMrp.collectAsState()
    var qrBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

    LaunchedEffect(productState) {
        productState?.let { p ->
            name = p.name
            barcode = p.barcode
            mrp = p.mrp.toString()
            brand = p.brand ?: ""
            quantity = (p.quantity ?: 1).toString()
            if (p.barcode.isNotEmpty()) {
                try {
                    qrBitmap = com.stocksnap.qr.QRGenerator.generateBitmap(p.barcode, 256)
                } catch (_: Exception) {
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Review Product", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(16.dp))

        productState?.let { p ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(p.frontImagePath)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(value = barcode, onValueChange = {}, label = { Text("Barcode (locked)") }, enabled = false)
        qrBitmap?.let { bmp ->
            Image(bitmap = bmp.asImageBitmap(), contentDescription = "QR Code", modifier = Modifier.padding(vertical = 8.dp))
        }
        if (previousMrp != null && previousMrp != productState?.mrp) {
            Text(
                text = "MRP Changed: Old ₹$previousMrp → New ₹${productState?.mrp}",
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        OutlinedTextField(value = mrp, onValueChange = { mrp = it }, label = { Text("MRP") })
        OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Brand") })
        OutlinedTextField(
            value = quantity,
            onValueChange = { input -> quantity = input.filter(Char::isDigit) },
            label = { Text("Quantity") }
        )

        Button(onClick = {
            val p = productState ?: return@Button
            val updated = p.copy(
                name = name,
                mrp = mrp.toDoubleOrNull() ?: p.mrp,
                brand = brand.ifEmpty { null },
                quantity = quantity.toIntOrNull() ?: p.quantity ?: 1,
                updatedAt = System.currentTimeMillis()
            )
            viewModel.updateProduct(updated)
            onSave()
        }) {
            Text(text = "Save Product")
        }
    }
}
