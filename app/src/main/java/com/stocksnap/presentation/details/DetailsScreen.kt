package com.stocksnap.presentation.details

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.domain.model.ProductStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Scale
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.material.icons.rounded.AssignmentTurnedIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    onBack: () -> Unit = {},
    onEditProduct: (Long) -> Unit = {},
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val arrivalState by viewModel.arrival.collectAsState()
    val productState by viewModel.product.collectAsState()

    val arrival = arrivalState ?: return
    val product = productState ?: return

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Arrival Delivery") },
            text = { Text("Are you sure you want to delete this arrival delivery record? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        viewModel.deleteArrival(onBack)
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF6F6F6))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Hero image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .background(Color.White)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.frontImagePath.ifEmpty { product.frontImageUrl })
                        .crossfade(true)
                        .build(),
                    contentDescription = "Product Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Top App Bar Contrast Gradient
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent)
                            )
                        )
                )

                // Image 1/1 count pill
                Surface(
                    color = Color.Black.copy(alpha = 0.45f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Image,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "1/1",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Title and Status
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = product.name.uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    fontSize = 22.sp,
                    lineHeight = 28.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Status Pill
                    val (statusBg, statusText, statusDot) = when (arrival.status) {
                        ProductStatus.PENDING -> Triple(Color(0xFFFFF3E0), Color(0xFFE65100), Color(0xFFFF9800))
                        ProductStatus.UPDATED -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), Color(0xFF4CAF50))
                    }
                    Surface(
                        color = statusBg,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(statusDot)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = arrival.status.name,
                                color = statusText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Added Pill
                    val isToday = android.text.format.DateUtils.isToday(arrival.createdAt)
                    val addedText = if (isToday) "ADDED TODAY" else {
                        val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                        "ADDED ON " + format.format(Date(arrival.createdAt)).uppercase(Locale.getDefault())
                    }
                    Surface(
                        color = Color(0xFFE3F2FD),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarToday,
                                contentDescription = null,
                                tint = Color(0xFF1565C0),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = addedText,
                                color = Color(0xFF1565C0),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

        // QR + Status Action Card (immediately visible, no scrolling needed)
        if (product.barcode.isNotEmpty()) {
            val qrBitmap = remember(product.barcode) {
                com.stocksnap.util.QrCodeGenerator.generate(product.barcode, 350)
            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Left Column — QR Code + Barcode value
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
                            .background(Color(0xFFFAFAFA), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        qrBitmap?.let { bmp ->
                            androidx.compose.foundation.Image(
                                bitmap = bmp.asImageBitmap(),
                                contentDescription = "QR Code",
                                modifier = Modifier
                                    .size(110.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.White),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = product.barcode,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Right Column — Status + Toggle Button
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 12.dp)
                    ) {
                        Text(
                            text = "Status",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Status chip
                        val (statusActionBg, statusActionText, statusActionDot) = when (arrival.status) {
                            ProductStatus.PENDING -> Triple(Color(0xFFFFF3E0), Color(0xFFE65100), Color(0xFFFF9800))
                            ProductStatus.UPDATED -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), Color(0xFF4CAF50))
                        }
                        Surface(
                            color = statusActionBg,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(statusActionDot)
                                )
                                Spacer(modifier = Modifier.width(7.dp))
                                Text(
                                    text = arrival.status.name,
                                    color = statusActionText,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Toggle status button
                        val toggleLabel = when (arrival.status) {
                            ProductStatus.PENDING -> "Mark Updated"
                            ProductStatus.UPDATED -> "Mark Pending"
                        }
                        val toggleBtnColor = when (arrival.status) {
                            ProductStatus.PENDING -> Color(0xFF2E7D32)
                            ProductStatus.UPDATED -> Color(0xFFE65100)
                        }
                        Button(
                            onClick = { viewModel.toggleProductStatus() },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = toggleBtnColor),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = toggleLabel,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Product Metadata 2x3 Grid Card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Row 1: Barcode & Code
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        GridItem(
                            label = "Barcode",
                            value = product.barcode,
                            iconType = "barcode",
                            onCopy = {
                                copyToClipboard(context, "Barcode", product.barcode)
                            }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(72.dp)
                            .background(Color(0xFFEEEEEE))
                            .align(Alignment.CenterVertically)
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        GridItem(
                            label = "Code",
                            value = product.code.ifEmpty { "Not Set" },
                            iconType = "code",
                            onCopy = if (product.code.isNotEmpty()) {
                                { copyToClipboard(context, "Code", product.code) }
                            } else null
                        )
                    }
                }

                Divider(color = Color(0xFFEEEEEE))

                // Row 2: Weight & MRP
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        GridItem(
                            label = "Weight",
                            value = product.weight.ifEmpty { "Not Set" },
                            iconType = "weight"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(72.dp)
                            .background(Color(0xFFEEEEEE))
                            .align(Alignment.CenterVertically)
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        GridItem(
                            label = "MRP",
                            value = "₹${arrival.mrp}",
                            iconType = "mrp"
                        )
                    }
                }

                Divider(color = Color(0xFFEEEEEE))

                // Row 3: Quantity & Status
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        GridItem(
                            label = "Quantity",
                            value = "${arrival.quantityReceived}",
                            iconType = "quantity"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(72.dp)
                            .background(Color(0xFFEEEEEE))
                            .align(Alignment.CenterVertically)
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        GridItem(
                            label = "Status",
                            value = arrival.status.name,
                            iconType = "status"
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Supplier Info Card (if present)
        arrival.optionalSupplierName?.let { supplier ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Supplier", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    Text(text = supplier, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Added/Updated Info Card
        val sdf = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Metadata & Collaborators", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(text = "Added By", fontSize = 11.sp, color = Color.Gray)
                        Text(text = arrival.createdByName, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = sdf.format(Date(arrival.createdAt)), fontSize = 11.sp, color = Color.Gray)
                    }
                    if (arrival.updatedByName != null) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Last Updated By", fontSize = 11.sp, color = Color.Gray)
                            Text(text = arrival.updatedByName, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = sdf.format(Date(arrival.updatedAt)), fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons (Edit and Delete)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { onEditProduct(product.id) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E))
            ) {
                Text(
                    text = "Edit Product",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = { showDeleteDialog = true },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(
                    text = "Delete",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }

    // Transparent floating Top App Bar
    TopAppBar(
        title = { Text("Product Details", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
} // closes Box
} // closes DetailsScreen function

@Composable
fun GridItem(
    label: String,
    value: String,
    iconType: String,
    onCopy: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 14.dp)
    ) {
        // Circle Icon
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8F5E9)),
            contentAlignment = Alignment.Center
        ) {
            GridIcon(type = iconType, color = Color(0xFF2E7D32))
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = value,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f, fill = false)
                )
                if (onCopy != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF0F0F0))
                            .clickable { onCopy() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ContentCopy,
                            contentDescription = "Copy",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GridIcon(type: String, color: Color) {
    val icon = when (type) {
        "barcode" -> Icons.Rounded.QrCodeScanner
        "code" -> Icons.Rounded.Code
        "weight" -> Icons.Rounded.Scale
        "mrp" -> Icons.Rounded.Payments
        "quantity" -> Icons.Rounded.Inventory2
        "status" -> Icons.Rounded.AssignmentTurnedIn
        else -> Icons.Rounded.QrCodeScanner
    }
    Icon(
        imageVector = icon,
        contentDescription = type,
        tint = color,
        modifier = Modifier.size(16.dp)
    )
}

private fun copyToClipboard(context: Context, label: String, value: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, value)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "$label copied to clipboard", Toast.LENGTH_SHORT).show()
}
