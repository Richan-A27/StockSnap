package com.stocksnap.presentation.dashboard

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Inbox
import androidx.compose.material.icons.rounded.Inventory
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Undo
import androidx.compose.material.icons.rounded.Unarchive
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.data.model.ActivityLog
import com.stocksnap.domain.model.ProductStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNewScan: () -> Unit = {},
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Profile Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Welcome back, ${currentUser?.name ?: "User"} 👋",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    )
                }

                // Profile Image with Active Dot
                Box(modifier = Modifier.size(48.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(currentUser?.photoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFAFAFA))
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2E7D32))
                        )
                    }
                }
            }
        }

        // 2. Today's Arrivals Statistics Card
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF0D4B2A))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Top Row: Icon, Title, Subtitle
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE8F5E9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Rounded.Unarchive, contentDescription = null, tint = Color(0xFF0D4B2A), modifier = Modifier.size(28.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Today’s Arrivals", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text("Here’s what’s happening today", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Stats box with thin border
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                // Total
                                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFF145E35)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Rounded.ShoppingBag, contentDescription = null, tint = Color(0xFF81C784), modifier = Modifier.size(20.dp))
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text("Total", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                                        Text(total.toString(), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                }
                                
                                // Divider
                                Box(modifier = Modifier.width(1.dp).height(50.dp).background(Color.White.copy(alpha = 0.15f)))
                                
                                // Pending
                                Row(modifier = Modifier.weight(1f).padding(start = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFF145E35)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Rounded.Schedule, contentDescription = null, tint = Color(0xFFFFB74D), modifier = Modifier.size(20.dp))
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text("Pending", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                                        Text(pending.toString(), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(color = Color.White.copy(alpha = 0.15f), thickness = 1.dp)
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Updated Today
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFF145E35)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Rounded.CheckCircleOutline, contentDescription = null, tint = Color(0xFF81C784), modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Updated Today", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                                    Text(updated.toString(), color = Color(0xFF81C784), fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. Scan Product Gradient Card Button
        item {
            ElevatedCard(
                onClick = onNewScan,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF007A48), Color(0xFF005330))
                            )
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // White viewfinder icon
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.QrCodeScanner,
                            contentDescription = null,
                            tint = Color(0xFF007A48),
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "+ Scan Product",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Scan barcode to add or update",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // 5. Filters Section
        item {
            FilterSection(viewModel = viewModel)
        }

        // 5. Recent Deliveries Section
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Deliveries",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.clickable { onViewAllDeliveries() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "View all",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006E3E)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = "View all",
                        tint = Color(0xFF006E3E),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        if (itemsState.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Rounded.Inbox, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "No stock arrivals recorded today.", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(itemsState.take(5)) { item ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onArrivalClick(item.arrival.arrivalId) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Product image thumbnail
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(item.product?.frontImagePath?.ifEmpty { item.product.frontImageUrl } ?: "")
                                .crossfade(true)
                                .build(),
                            contentDescription = "Product Image",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFFAFAFA)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // Product text detail
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.arrival.productName,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            if (item.product != null && item.product.code.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Code: ${item.product.code}",
                                    fontSize = 12.sp,
                                    color = Color.DarkGray
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Qty: ${item.arrival.quantityReceived}   •   MRP: ₹${item.arrival.mrp}",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Status pill and navigation chevron
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            StatusChip(status = item.arrival.status)
                            Icon(
                                imageVector = Icons.Rounded.ChevronRight,
                                contentDescription = "Open",
                                tint = Color.LightGray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // 6. Recent Activity Section
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Activity",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.clickable { onViewAllActivity() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "View all",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006E3E)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = "View all",
                        tint = Color(0xFF006E3E),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        if (activityLogsState.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Rounded.History, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "No activities registered yet.", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }
        } else {
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        activityLogsState.take(3).forEachIndexed { index, log ->
                            ActivityRow(log = log)
                            if (index < activityLogsState.take(3).size - 1) {
                                Divider(color = Color(0xFFF0F0F0))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(
    modifier: Modifier = Modifier,
    label: String,
    count: Int,
    color: Color
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.toString(),
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color,
            letterSpacing = (-0.5).sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            lineHeight = 14.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun StatusChip(status: ProductStatus) {
    val bgColor: Color
    val textColor: Color

    when (status) {
        ProductStatus.PENDING -> {
            bgColor = Color(0xFFFFF3E0)
            textColor = Color(0xFFE65100) // Orange
        }
        ProductStatus.UPDATED -> {
            bgColor = Color(0xFFE8F5E9)
            textColor = Color(0xFF2E7D32) // Green
        }
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 10.sp,
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
            Triple(Color(0xFFE8F5E9), "check", Color(0xFF2E7D32))
        log.actionType.contains("DISABLED") || log.actionType.contains("REJECTED") -> 
            Triple(Color(0xFFFFEBEE), "block", Color(0xFFC62828))
        log.actionType.contains("MARKED_PENDING") -> 
            Triple(Color(0xFFFFF3E0), "undo", Color(0xFFE65100))
        log.actionType.contains("CHANGED") || log.actionType.contains("UPDATED") -> 
            Triple(Color(0xFFE3F2FD), "edit", Color(0xFF1565C0))
        log.actionType.contains("CREATED") || log.actionType.contains("ADD") -> 
            Triple(Color(0xFFF3E5F5), "plus", Color(0xFF7B1FA2))
        log.actionType.contains("SCAN") -> 
            Triple(Color(0xFFFFF3E0), "inventory", Color(0xFFE65100))
        else -> 
            Triple(Color(0xFFFFF3E0), "inventory", Color(0xFFE65100))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(circleBg),
            contentAlignment = Alignment.Center
        ) {
            ActivityIcon(type = iconType, color = iconColor)
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Text Content
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${log.performedByName} ${getActionDescription(log)}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Time
        Text(
            text = timeStr,
            fontSize = 11.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
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
        modifier = Modifier.size(16.dp)
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
        minutes < 60 -> "$minutes min${if (minutes > 1) "s" else ""} ago"
        hours < 24 -> "$hours hr${if (hours > 1) "s" else ""} ago"
        days == 1L -> "yesterday"
        else -> "$days days ago"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(viewModel: DashboardViewModel) {
    val selectedStatus by viewModel.selectedStatus.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedEmployee by viewModel.selectedEmployee.collectAsState()
    val employeeList by viewModel.employeeList.collectAsState()
    var showEmployeeMenu by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
        // Status Row
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val statuses = listOf("All", "Pending", "Updated")
            items(statuses) { status ->
                FilterChip(
                    selected = selectedStatus == status,
                    onClick = { viewModel.setStatusFilter(status) },
                    label = { Text(status) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF006E3E),
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Date Row
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val dates = listOf("Today", "Yesterday", "Last 7 Days", "This Month")
            items(dates) { date ->
                FilterChip(
                    selected = selectedDate == date,
                    onClick = { viewModel.setDateFilter(date) },
                    label = { Text(date) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF006E3E),
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Employee Row
        Box(modifier = Modifier.fillMaxWidth()) {
            FilterChip(
                selected = selectedEmployee != "All Employees",
                onClick = { showEmployeeMenu = true },
                label = { Text(if (selectedEmployee == "All Employees") "All Staff ▼" else "$selectedEmployee ▼") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF006E3E),
                    selectedLabelColor = Color.White
                )
            )
            DropdownMenu(
                expanded = showEmployeeMenu,
                onDismissRequest = { showEmployeeMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("All Employees") },
                    onClick = {
                        viewModel.setEmployeeFilter("All Employees")
                        showEmployeeMenu = false
                    }
                )
                employeeList.forEach { emp ->
                    DropdownMenuItem(
                        text = { Text(emp) },
                        onClick = {
                            viewModel.setEmployeeFilter(emp)
                            showEmployeeMenu = false
                        }
                    )
                }
            }
        }
    }
}
