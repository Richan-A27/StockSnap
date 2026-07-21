package com.stocksnap.presentation.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stocksnap.data.model.ActivityLog
import com.stocksnap.presentation.components.GlassListItem
import com.stocksnap.presentation.components.GlassSearchBar
import com.stocksnap.presentation.components.GlassSegmentedControl
import com.stocksnap.ui.theme.AppBackground
import com.stocksnap.ui.theme.PrimaryGreen
import com.stocksnap.ui.theme.StatusDanger
import com.stocksnap.ui.theme.StatusWarning
import com.stocksnap.ui.theme.TextPrimary
import com.stocksnap.ui.theme.TextSecondary
import android.text.format.DateUtils
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityLogsScreen(
    onBack: () -> Unit,
    viewModel: ActivityLogsViewModel = hiltViewModel()
) {
    val logs by viewModel.filteredLogs.collectAsState()
    val selectedStatus by viewModel.selectedStatus.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedEmployee by viewModel.selectedEmployee.collectAsState()
    val employeeList by viewModel.employeeList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

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
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar
            TopAppBar(
                title = { Text("Activity Logs", style = MaterialTheme.typography.titleLarge, color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

            // Filters Section
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                // Search Bar
                GlassSearchBar(
                    query = searchQuery,
                    onQueryChange = { viewModel.setSearchQuery(it) },
                    onSearch = { },
                    placeholder = "Search logs...",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Status Segments
                val statuses = listOf("All", "Created", "Updated", "Approved", "Disabled")
                GlassSegmentedControl(
                    items = statuses,
                    selectedIndex = statuses.indexOf(selectedStatus).coerceAtLeast(0),
                    onItemSelected = { viewModel.setStatus(statuses[it]) }
                )
                
                Spacer(modifier = Modifier.height(12.dp))

                // Date Segments
                val dates = listOf("All Time", "Today", "Yesterday", "7 Days", "Month")
                val dateIndex = when (selectedDate) {
                    "All Time" -> 0
                    "Today" -> 1
                    "Yesterday" -> 2
                    "Last 7 Days" -> 3
                    "This Month" -> 4
                    else -> 0
                }
                GlassSegmentedControl(
                    items = dates,
                    selectedIndex = dateIndex,
                    onItemSelected = { 
                        val filter = when(it) {
                            0 -> "All Time"
                            1 -> "Today"
                            2 -> "Yesterday"
                            3 -> "Last 7 Days"
                            4 -> "This Month"
                            else -> "All Time"
                        }
                        viewModel.setDate(filter)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Employee Dropdown (styled as a chip)
                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.padding(bottom = 16.dp)) {
                    Surface(
                        color = Color.White.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.clickable { expanded = true }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(text = "Employee: $selectedEmployee", style = MaterialTheme.typography.bodySmall, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Rounded.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = TextPrimary
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(AppBackground)
                    ) {
                        employeeList.forEach { emp ->
                            DropdownMenuItem(
                                text = { Text(emp, color = TextPrimary) },
                                onClick = {
                                    viewModel.setEmployee(emp)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Logs List
            if (logs.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = "No logs found matching filters.", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(logs) { log ->
                        ActivityLogCard(log = log)
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityLogCard(log: ActivityLog) {
    GlassListItem {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val (circleBg, iconType, iconColor) = getActivityIconAndColors(log.actionType)
            
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(circleBg),
                contentAlignment = Alignment.Center
            ) {
                val icon = when (iconType) {
                    "check" -> Icons.Rounded.CheckCircle
                    "block" -> Icons.Rounded.Block
                    "edit" -> Icons.Rounded.Edit
                    "plus" -> Icons.Rounded.Add
                    "undo" -> Icons.Rounded.Undo
                    "merge" -> Icons.Rounded.CallMerge
                    else -> Icons.Rounded.Inventory
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                val actionDesc = getActionDescription(log)
                Text(
                    text = "${log.performedByName} $actionDesc",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Code: ${log.barcode.takeLast(6)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                    Text(
                        text = getRelativeTime(log.timestamp),
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

private fun getActivityIconAndColors(actionType: String): Triple<Color, String, Color> {
    return when {
        actionType.contains("MERGED") ->
            Triple(Color(0xFF0097A7).copy(alpha = 0.1f), "merge", Color(0xFF0097A7))
        actionType.contains("APPROVED") || actionType.contains("ENABLED") -> 
            Triple(PrimaryGreen.copy(alpha = 0.1f), "check", PrimaryGreen)
        actionType.contains("DISABLED") || actionType.contains("REJECTED") -> 
            Triple(StatusDanger.copy(alpha = 0.1f), "block", StatusDanger)
        actionType.contains("MARKED_PENDING") -> 
            Triple(StatusWarning.copy(alpha = 0.1f), "undo", StatusWarning)
        actionType.contains("CHANGED") || actionType.contains("UPDATED") -> 
            Triple(Color(0xFF34C759).copy(alpha = 0.1f), "edit", Color(0xFF34C759))
        actionType.contains("CREATED") || actionType.contains("ADD") -> 
            Triple(Color(0xFF5E5CE6).copy(alpha = 0.1f), "plus", Color(0xFF5E5CE6))
        actionType.contains("SCAN") -> 
            Triple(StatusWarning.copy(alpha = 0.1f), "inventory", StatusWarning)
        else -> 
            Triple(StatusWarning.copy(alpha = 0.1f), "inventory", StatusWarning)
    }
}

private fun getActionDescription(log: ActivityLog): String {
    return when (log.actionType) {
        "PRODUCT_QUANTITY_MERGED" -> "merged quantities for ${log.productName}"
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
            val typeDesc = log.actionType.lowercase(Locale.getDefault()).replace("_", " ")
            "$typeDesc ${log.productName}"
        }
    }
}

private fun getRelativeTime(timeInMillis: Long): String {
    val now = System.currentTimeMillis()
    if (now - timeInMillis < DateUtils.MINUTE_IN_MILLIS) {
        return "Just now"
    }
    return DateUtils.getRelativeTimeSpanString(
        timeInMillis,
        now,
        DateUtils.MINUTE_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE
    ).toString()
}
