package com.stocksnap.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Inbox
import androidx.compose.material.icons.rounded.Inventory
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.Unarchive
import androidx.compose.material.icons.rounded.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.data.model.ActivityLog
import com.stocksnap.domain.model.ProductStatus
import com.stocksnap.presentation.components.GlassCard
import com.stocksnap.presentation.components.GlassListItem
import com.stocksnap.presentation.components.GlassSegmentedControl
import com.stocksnap.ui.theme.AppBackground
import com.stocksnap.ui.theme.PrimaryGreen
import com.stocksnap.ui.theme.StatusDanger
import com.stocksnap.ui.theme.StatusWarning
import com.stocksnap.ui.theme.TextPrimary
import com.stocksnap.ui.theme.TextSecondary
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onBarcodeScan: () -> Unit = {},
    onArrivalClick: (String) -> Unit = {},
    onViewAllDeliveries: () -> Unit = {},
    onViewAllActivity: () -> Unit = {},
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val itemsState by viewModel.items.collectAsState()
    val activityLogsState by viewModel.activityLogs.collectAsState()
    val total by viewModel.total.collectAsState()
    val updated by viewModel.updated.collectAsState()
    val pending by viewModel.pending.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        // Ambient glass background blurs
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize().blur(80.dp)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PrimaryGreen.copy(alpha = 0.12f), Color.Transparent)
                ),
                radius = size.width,
                center = Offset(0f, 0f)
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PrimaryGreen.copy(alpha = 0.08f), Color.Transparent)
                ),
                radius = size.width * 0.8f,
                center = Offset(size.width, size.height * 0.5f)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 100.dp), // Bottom padding for nav bar
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 1. Profile Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Hello, ${currentUser?.name?.substringBefore(" ") ?: "User"}",
                            style = MaterialTheme.typography.displayMedium,
                            color = TextPrimary
                        )
                        Text(
                            text = "Ready to manage some inventory? 👋",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Box(modifier = Modifier.size(56.dp)) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(currentUser?.photoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(AppBackground)
                                .align(Alignment.BottomEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryGreen)
                            )
                        }
                    }
                }
            }

            // 2. Today's Arrivals Statistics GlassCard
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryGreen.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Rounded.Unarchive, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Today’s Arrivals", style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
                                Text("Quick overview of today's stats", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            StatItem(
                                modifier = Modifier.weight(1f),
                                label = "Total",
                                count = total,
                                icon = Icons.Rounded.ShoppingBag,
                                color = PrimaryGreen
                            )
                            Divider(modifier = Modifier.width(1.dp).height(40.dp), color = TextSecondary.copy(alpha = 0.2f))
                            StatItem(
                                modifier = Modifier.weight(1f),
                                label = "Pending",
                                count = pending,
                                icon = Icons.Rounded.Schedule,
                                color = StatusWarning
                            )
                            Divider(modifier = Modifier.width(1.dp).height(40.dp), color = TextSecondary.copy(alpha = 0.2f))
                            StatItem(
                                modifier = Modifier.weight(1f),
                                label = "Updated",
                                count = updated,
                                icon = Icons.Rounded.CheckCircleOutline,
                                color = PrimaryGreen
                            )
                        }
                    }
                }
            }

            // 3. Scan Action
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth().clickable { onBarcodeScan() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(PrimaryGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.QrCodeScanner,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Scan Product",
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary
                            )
                            Text(
                                text = "Use camera to scan barcode instantly",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                        Icon(
                            imageVector = Icons.Rounded.ChevronRight,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // 4. Filters Section (Segmented Controls instead of chips)
            item {
                FilterSection(viewModel = viewModel)
            }

            // 5. Recent Deliveries Section
            item {
                SectionHeader("Recent Deliveries", onViewAllDeliveries)
            }

            if (itemsState.isEmpty()) {
                item {
                    EmptyStateCard(icon = Icons.Rounded.Inbox, message = "No stock arrivals recorded today.")
                }
            } else {
                items(itemsState.take(5)) { item ->
                    GlassListItem(
                        modifier = Modifier.padding(bottom = 12.dp),
                        onClick = { onArrivalClick(item.arrival.arrivalId) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(item.product?.frontImagePath?.ifEmpty { item.product.frontImageUrl } ?: "")
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Product Image",
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White.copy(alpha = 0.5f)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.arrival.productName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary
                                )
                                if (item.product != null && item.product.code.isNotEmpty()) {
                                    Text(
                                        text = "Code: ${item.product.code}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Qty: ${item.arrival.quantityReceived}   •   MRP: ₹${item.arrival.mrp}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            StatusChip(status = item.arrival.status)
                        }
                    }
                }
            }

            // 6. Recent Activity Section
            item {
                SectionHeader("Recent Activity", onViewAllActivity)
            }

            if (activityLogsState.isEmpty()) {
                item {
                    EmptyStateCard(icon = Icons.Rounded.History, message = "No activities registered yet.")
                }
            } else {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                            activityLogsState.take(3).forEachIndexed { index, log ->
                                ActivityRow(log = log)
                                if (index < activityLogsState.take(3).size - 1) {
                                    Divider(color = TextSecondary.copy(alpha = 0.1f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, onViewAll: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary
        )
        Row(
            modifier = Modifier.clickable { onViewAll() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "See All",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryGreen
            )
        }
    }
}

@Composable
fun StatItem(
    modifier: Modifier = Modifier,
    label: String,
    count: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp).padding(bottom = 4.dp))
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.displayMedium,
            color = TextPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EmptyStateCard(icon: androidx.compose.ui.graphics.vector.ImageVector, message: String) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = TextSecondary.copy(alpha = 0.5f), modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = message, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        }
    }
}

@Composable
fun StatusChip(status: ProductStatus) {
    val bgColor: Color
    val textColor: Color

    when (status) {
        ProductStatus.PENDING -> {
            bgColor = StatusWarning.copy(alpha = 0.15f)
            textColor = StatusWarning
        }
        ProductStatus.UPDATED -> {
            bgColor = PrimaryGreen.copy(alpha = 0.15f)
            textColor = PrimaryGreen
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = status.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun ActivityRow(log: ActivityLog) {
    val timeStr = getRelativeTime(log.timestamp)
    val (circleBg, iconType, iconColor) = when {
        log.actionType.contains("APPROVED") || log.actionType.contains("ENABLED") -> 
            Triple(PrimaryGreen.copy(alpha = 0.1f), "check", PrimaryGreen)
        log.actionType.contains("DISABLED") || log.actionType.contains("REJECTED") -> 
            Triple(StatusDanger.copy(alpha = 0.1f), "block", StatusDanger)
        log.actionType.contains("MARKED_PENDING") -> 
            Triple(StatusWarning.copy(alpha = 0.1f), "undo", StatusWarning)
        log.actionType.contains("CHANGED") || log.actionType.contains("UPDATED") -> 
            Triple(Color(0xFF34C759).copy(alpha = 0.1f), "edit", Color(0xFF34C759))
        log.actionType.contains("CREATED") || log.actionType.contains("ADD") -> 
            Triple(Color(0xFF5E5CE6).copy(alpha = 0.1f), "plus", Color(0xFF5E5CE6))
        log.actionType.contains("SCAN") -> 
            Triple(StatusWarning.copy(alpha = 0.1f), "inventory", StatusWarning)
        else -> 
            Triple(StatusWarning.copy(alpha = 0.1f), "inventory", StatusWarning)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(circleBg),
            contentAlignment = Alignment.Center
        ) {
            ActivityIcon(type = iconType, color = iconColor)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${log.performedByName} ${getActionDescription(log)}",
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
            Text(
                text = timeStr,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun ActivityIcon(type: String, color: Color) {
    val icon = when (type) {
        "check" -> Icons.Rounded.CheckCircle
        "block" -> Icons.Rounded.Block
        "edit" -> Icons.Rounded.Edit
        "plus" -> Icons.Rounded.Add
        "undo" -> Icons.Rounded.Undo
        else -> Icons.Rounded.Inventory
    }
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = color,
        modifier = Modifier.size(18.dp)
    )
}

private fun getActionDescription(log: ActivityLog): String {
    return when (log.actionType) {
        "USER_APPROVED" -> "approved a user account"
        "USER_REJECTED" -> "rejected a user account"
        "USER_DISABLED" -> "disabled a user account"
        "USER_ENABLED" -> "enabled a user"
        "PRODUCT_UPDATED" -> "updated ${log.productName}"
        "PRODUCT_MARKED_PENDING" -> "marked ${log.productName} as Pending"
        "PRODUCT_CREATED" -> "created ${log.productName}"
        "PRODUCT_DELETED" -> "deleted ${log.productName}"
        "STATUS_CHANGED" -> "updated status of ${log.productName}"
        "ARRIVAL_UPDATED" -> "updated arrival details of ${log.productName}"
        "ARRIVAL_DELETED" -> "deleted delivery of ${log.productName}"
        "ARRIVAL_CREATED" -> "added delivery of ${log.productName}"
        "SCAN_COMPLETED" -> "scanned ${log.productName}"
        else -> {
            val typeDesc = log.actionType.lowercase(Locale.getDefault())
                .replace("_", " ")
            "$typeDesc ${log.productName}"
        }
    }
}

private fun getRelativeTime(timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        diff < 0 -> "just now"
        seconds < 60 -> "just now"
        minutes < 60 -> "${minutes}m ago"
        hours < 24 -> "${hours}h ago"
        days == 1L -> "yesterday"
        else -> "${days}d ago"
    }
}

@Composable
fun FilterSection(viewModel: DashboardViewModel) {
    val selectedStatus by viewModel.selectedStatus.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        val statuses = listOf("All", "Pending", "Updated")
        GlassSegmentedControl(
            items = statuses,
            selectedIndex = statuses.indexOf(selectedStatus).coerceAtLeast(0),
            onItemSelected = { viewModel.setStatusFilter(statuses[it]) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        val dates = listOf("Today", "Yesterday", "7 Days", "Month")
        val dateIndex = when (selectedDate) {
            "Today" -> 0
            "Yesterday" -> 1
            "Last 7 Days" -> 2
            "This Month" -> 3
            else -> 0
        }
        GlassSegmentedControl(
            items = dates,
            selectedIndex = dateIndex,
            onItemSelected = { 
                val filter = when(it) {
                    0 -> "Today"
                    1 -> "Yesterday"
                    2 -> "Last 7 Days"
                    3 -> "This Month"
                    else -> "Today"
                }
                viewModel.setDateFilter(filter) 
            }
        )
    }
}
