package com.stocksnap.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.hilt.navigation.compose.hiltViewModel
import com.stocksnap.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onNewScan: () -> Unit = {}, onProductClick: (Long) -> Unit = {}, viewModel: DashboardViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.loadToday()
    }

    val itemsState = viewModel.items.collectAsState()
    val total by viewModel.total.collectAsState()
    val updated by viewModel.updated.collectAsState()
    val pending by viewModel.pending.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(16.dp)) {

        Text(text = "Today's Scans", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        androidx.compose.material3.ElevatedCard(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                StatItem(label = "Total", count = total, color = Color(0xFF006E3E))
                StatItem(label = "Updated", count = updated, color = Color(0xFF6750A4))
                StatItem(label = "Pending", count = pending, color = Color(0xFFE67E22))
            }
        }

        Button(
            onClick = onNewScan,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E))
        ) {
            Icon(painter = painterResource(android.R.drawable.ic_menu_camera), contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(8.dp))
            Text(text = "New Scan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        Text(text = "Recent Scans", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(itemsState.value) { product ->
                androidx.compose.material3.ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { onProductClick(product.id) }
                ) {
                     Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                         AsyncImage(
                             model = ImageRequest.Builder(LocalContext.current)
                                 .data(product.frontImagePath)
                                 .crossfade(true)
                                 .build(),
                             contentDescription = "Product Image",
                             modifier = Modifier.size(64.dp).clip(RoundedCornerShape(8.dp)),
                             contentScale = ContentScale.Crop
                         )
                         
                         Spacer(modifier = Modifier.width(16.dp))

                         Column(modifier = Modifier.weight(1f)) {
                             Text(text = product.name.ifEmpty { "Generic Product" }, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                             Text(text = "MRP: ₹${product.mrp}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                             Spacer(Modifier.height(4.dp))
                             StatusChip(status = product.status)
                         }
                         Icon(painter = painterResource(android.R.drawable.ic_media_play), contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, count: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count.toString(), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = color)
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun StatusChip(status: com.stocksnap.domain.model.ProductStatus) {
    val isPending = status == com.stocksnap.domain.model.ProductStatus.PENDING
    val bgColor = if (isPending) Color(0xFFFFE5D9) else Color(0xFFE8F5E9)
    val textColor = if (isPending) Color(0xFFE67E22) else Color(0xFF2E7D32)
    
    Surface(color = bgColor, shape = RoundedCornerShape(4.dp)) {
        Text(
            text = if (isPending) "PENDING" else "UPDATED",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
