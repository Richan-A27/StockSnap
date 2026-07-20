package com.stocksnap.presentation.activity

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stocksnap.data.model.ActivityLog
import java.text.SimpleDateFormat
import java.util.*
import android.text.format.DateUtils

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Activity Logs", fontWeight = FontWeight.ExtraBold, color = Color.Black) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8F9FA))
        )

        // Filters Section
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                placeholder = { Text("Search logs...", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = "Search", tint = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF006E3E),
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            // Status Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                val statuses = listOf("All", "Created", "Updated", "Approved", "Disabled")
                items(statuses.size) { index ->
                    val status = statuses[index]
                    val isSelected = status == selectedStatus
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setStatus(status) },
                        label = { Text(status) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF006E3E),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Date Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                val dates = listOf("All Time", "Today", "Yesterday", "Last 7 Days", "This Month")
                items(dates.size) { index ->
                    val date = dates[index]
                    val isSelected = date == selectedDate
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setDate(date) },
                        label = { Text(date) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF006E3E),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Employee Dropdown
            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.padding(bottom = 12.dp)) {
                OutlinedButton(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = "Employee: $selectedEmployee", color = Color(0xFF006E3E))
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF006E3E)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    employeeList.forEach { emp ->
                        DropdownMenuItem(
                            text = { Text(emp, color = Color.Black) },
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No logs found matching filters.", color = Color.Gray, fontSize = 14.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(logs) { log ->
                    ActivityLogCard(log = log)
                }
            }
        }
    }
}

@Composable
fun ActivityLogCard(log: ActivityLog) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val (circleBg, iconType, iconColor) = getActivityIconAndColors(log.actionType)
            
            Box(
                modifier = Modifier
                    .size(40.dp)
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
                    else -> Icons.Rounded.Inventory
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                val actionDesc = getActionDescription(log)
                Text(
                    text = "${log.performedByName} $actionDesc",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
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
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = getRelativeTime(log.timestamp),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

private fun getActivityIconAndColors(actionType: String): Triple<Color, String, Color> {
    return when {
        actionType.contains("APPROVED") || actionType.contains("ENABLED") -> 
            Triple(Color(0xFFE8F5E9), "check", Color(0xFF2E7D32))
        actionType.contains("DISABLED") || actionType.contains("REJECTED") -> 
            Triple(Color(0xFFFFEBEE), "block", Color(0xFFC62828))
        actionType.contains("MARKED_PENDING") -> 
            Triple(Color(0xFFFFF3E0), "undo", Color(0xFFE65100))
        actionType.contains("CHANGED") || actionType.contains("UPDATED") -> 
            Triple(Color(0xFFE3F2FD), "edit", Color(0xFF1565C0))
        actionType.contains("CREATED") || actionType.contains("ADD") -> 
            Triple(Color(0xFFF3E5F5), "plus", Color(0xFF7B1FA2))
        actionType.contains("SCAN") -> 
            Triple(Color(0xFFFFF3E0), "inventory", Color(0xFFE65100))
        else -> 
            Triple(Color(0xFFFFF3E0), "inventory", Color(0xFFE65100))
    }
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
