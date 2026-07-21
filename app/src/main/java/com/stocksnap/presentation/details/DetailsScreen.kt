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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.domain.model.ProductStatus
import com.stocksnap.presentation.components.GlassButton
import com.stocksnap.presentation.components.GlassCard
import com.stocksnap.ui.theme.AppBackground
import com.stocksnap.ui.theme.PrimaryGreen
import com.stocksnap.ui.theme.StatusDanger
import com.stocksnap.ui.theme.StatusWarning
import com.stocksnap.ui.theme.TextPrimary
import com.stocksnap.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
                    Text("Delete", color = StatusDanger)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = TextPrimary)
                }
            },
            containerColor = AppBackground,
            titleContentColor = TextPrimary,
            textContentColor = TextSecondary
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        // Ambient glass background blurs
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize().blur(80.dp)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PrimaryGreen.copy(alpha = 0.12f), Color.Transparent)
                ),
                radius = size.width,
                center = Offset(size.width, size.height * 0.2f)
            )
        }

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
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent)
                            )
                        )
                )

                // Image 1/1 count pill
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.45f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
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
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Title and Status
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = product.name.uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.displaySmall,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Status Pill
                    val (statusBg, statusText, statusDot) = when (arrival.status) {
                        ProductStatus.PENDING -> Triple(StatusWarning.copy(alpha = 0.15f), StatusWarning, StatusWarning)
                        ProductStatus.UPDATED -> Triple(PrimaryGreen.copy(alpha = 0.15f), PrimaryGreen, PrimaryGreen)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(statusBg)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
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
                                style = MaterialTheme.typography.labelSmall,
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
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF5E5CE6).copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarToday,
                                contentDescription = null,
                                tint = Color(0xFF5E5CE6),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = addedText,
                                color = Color(0xFF5E5CE6),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // QR + Status Action Card
            if (product.barcode.isNotEmpty()) {
                val qrBitmap = remember(product.barcode) {
                    com.stocksnap.util.QrCodeGenerator.generate(product.barcode, 350)
                }

                GlassCard(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Left Column — QR Code + Barcode value
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, TextSecondary.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
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
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
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
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Status chip
                            val (statusActionBg, statusActionText, statusActionDot) = when (arrival.status) {
                                ProductStatus.PENDING -> Triple(StatusWarning.copy(alpha = 0.15f), StatusWarning, StatusWarning)
                                ProductStatus.UPDATED -> Triple(PrimaryGreen.copy(alpha = 0.15f), PrimaryGreen, PrimaryGreen)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(statusActionBg)
                                    .padding(horizontal = 14.dp, vertical = 7.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
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
                                        style = MaterialTheme.typography.labelLarge,
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
                                ProductStatus.PENDING -> PrimaryGreen
                                ProductStatus.UPDATED -> StatusWarning
                            }
                            GlassButton(
                                text = toggleLabel,
                                onClick = { viewModel.toggleProductStatus() },
                                containerColor = toggleBtnColor,
                                contentColor = Color.White
                            )
                        }
                    }
                }
            }

            // Product Metadata Grid GlassCard
            GlassCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp)
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
                                .height(80.dp)
                                .background(TextSecondary.copy(alpha = 0.1f))
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

                    Divider(color = TextSecondary.copy(alpha = 0.1f))

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
                                .height(80.dp)
                                .background(TextSecondary.copy(alpha = 0.1f))
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

                    Divider(color = TextSecondary.copy(alpha = 0.1f))

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
                                .height(80.dp)
                                .background(TextSecondary.copy(alpha = 0.1f))
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

            // Supplier Info Card (if present)
            arrival.optionalSupplierName?.let { supplier ->
                GlassCard(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Supplier", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Text(text = supplier, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                }
            }

            // Added/Updated Info Card
            val sdf = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
            GlassCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Metadata & Collaborators", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "Added By", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                            Text(text = arrival.createdByName, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            Text(text = sdf.format(Date(arrival.createdAt)), style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                        }
                        if (arrival.updatedByName != null) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "Last Updated By", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                                Text(text = arrival.updatedByName, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                Text(text = sdf.format(Date(arrival.updatedAt)), style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons (Edit and Delete)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    GlassButton(
                        text = "Edit Product",
                        onClick = { onEditProduct(product.id) }
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    GlassButton(
                        text = "Delete",
                        onClick = { showDeleteDialog = true },
                        containerColor = StatusDanger,
                        contentColor = Color.White
                    )
                }
            }
        }

        // Transparent floating Top App Bar
        TopAppBar(
            title = { Text("Product Details", style = MaterialTheme.typography.titleLarge, color = Color.White) },
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
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        // Circle Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(PrimaryGreen.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            GridIcon(type = iconType, color = PrimaryGreen)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f, fill = false)
                )
                if (onCopy != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(TextSecondary.copy(alpha = 0.1f))
                            .clickable { onCopy() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ContentCopy,
                            contentDescription = "Copy",
                            tint = TextSecondary,
                            modifier = Modifier.size(12.dp)
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
        modifier = Modifier.size(20.dp)
    )
}

private fun copyToClipboard(context: Context, label: String, value: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, value)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "$label copied to clipboard", Toast.LENGTH_SHORT).show()
}
