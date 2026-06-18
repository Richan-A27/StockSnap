package com.stocksnap.presentation.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onProductClick: (Long) -> Unit = {}
) {
    val historyState by viewModel.historyState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "History Sessions",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (historyState.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No history found")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                historyState.forEach { (date, products) ->
                    item {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(products) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { onProductClick(product.id) },
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(product.frontImagePath)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Product Image",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = product.name.ifEmpty { "Generic Product" }, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                                    Text(text = "Barcode: ${product.barcode}", style = MaterialTheme.typography.bodySmall)
                                }
                                Text(
                                    text = product.status.name,
                                    color = if (product.status.name == "PENDING") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
