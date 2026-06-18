package com.stocksnap.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.presentation.dashboard.StatusChip
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailsScreen(
    onBack: () -> Unit = {},
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val productState = viewModel.product.collectAsState()
    val qrState = viewModel.qrBitmap.collectAsState()
    val p = productState.value ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_revert),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { /* more options */ }) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_more),
                    contentDescription = "More",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Hero Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(p.frontImagePath)
                .crossfade(true)
                .build(),
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(24.dp))

        // Product Name
        Text(
            text = p.name.uppercase(Locale.getDefault()).ifEmpty { "GENERIC PRODUCT" },
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 24.dp),
            letterSpacing = 0.5.sp
        )

        Spacer(Modifier.height(20.dp))

        // MRP & Quantity Card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // MRP
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFFF6F6F6),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_edit),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = Color.Gray
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(text = "MRP", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text(text = "₹${p.mrp}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    }
                }
                
                Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color(0xFFF0F0F0))

                // Quantity
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFFF6F6F6),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_add),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = Color.Gray
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(text = "Quantity", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text(text = "${p.quantity ?: 1}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // QR Code & Barcode Card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display QR code from StateFlow
                qrState.value?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "QR Code",
                        modifier = Modifier
                            .size(110.dp)
                            .background(Color.White)
                    )
                } ?: Box(modifier = Modifier.size(110.dp).background(Color(0xFFF6F6F6)), contentAlignment = Alignment.Center) {
                    Text("QR", color = Color.LightGray)
                }
                
                Spacer(Modifier.width(20.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Barcode", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(
                        text = p.barcode, 
                        style = MaterialTheme.typography.bodyLarge, 
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Status Card
        MinimalCard(label = "Status", trailing = { StatusChip(status = p.status) })

        Spacer(Modifier.height(16.dp))

        // Created Card
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        MinimalCard(label = "Created", trailing = {
            Text(text = sdf.format(Date(p.createdAt)), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        })

        Spacer(Modifier.height(32.dp))

        // Action Button
        Button(
            onClick = { viewModel.toggleStatus() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E))
        ) {
            Text(
                text = if (p.status == com.stocksnap.domain.model.ProductStatus.PENDING) "Mark as Updated" else "Reset to Pending",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MinimalCard(label: String, trailing: @Composable () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            trailing()
        }
    }
}
