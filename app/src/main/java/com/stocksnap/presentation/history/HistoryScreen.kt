package com.stocksnap.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.presentation.dashboard.StatusChip

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onArrivalClick: (String) -> Unit = {}
) {
    val historyState by viewModel.historyState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        Text(
            text = "Arrival History Log",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp),
            letterSpacing = (-0.5).sp
        )

        val selectedStatus by viewModel.selectedStatus.collectAsState()
        val selectedDate by viewModel.selectedDate.collectAsState()
        val selectedEmployee by viewModel.selectedEmployee.collectAsState()
        val employeeList by viewModel.employeeList.collectAsState()

        // Filters Section
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            // Status Chips
            androidx.compose.foundation.lazy.LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                val statuses = listOf("All", "PENDING", "UPDATED")
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
            androidx.compose.foundation.lazy.LazyRow(
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
            var expanded by remember { androidx.compose.runtime.mutableStateOf(false) }
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = "Employee: $selectedEmployee", color = Color(0xFF006E3E))
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Rounded.ArrowDropDown,
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

        if (historyState.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No history found", color = Color.Gray, fontSize = 14.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                historyState.forEach { (date, arrivals) ->
                    item {
                        Text(
                            text = date,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF006E3E),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    items(arrivals) { item ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onArrivalClick(item.arrival.arrivalId) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Product thumbnail image
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(item.product?.frontImagePath?.ifEmpty { item.product.frontImageUrl } ?: "")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Product Image",
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFFAFAFA)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                // Product detail column
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.arrival.productName,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (item.product != null && item.product.code.isNotEmpty()) {
                                            Surface(
                                                color = Color(0xFFE8F5E9),
                                                shape = RoundedCornerShape(4.dp),
                                                modifier = Modifier.padding(end = 6.dp)
                                            ) {
                                                Text(
                                                    text = item.product.code,
                                                    color = Color(0xFF2E7D32),
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "•  ${item.product?.weight?.ifEmpty { "N/A" } ?: "N/A"}",
                                            fontSize = 11.sp,
                                            color = Color.Gray,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Qty: ${item.arrival.quantityReceived}   •   MRP: ₹${item.arrival.mrp}",
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "Created by: ${item.arrival.createdByName}",
                                        fontSize = 10.sp,
                                        color = Color.Gray.copy(alpha = 0.8f)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                // Status chip and chevron
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
            }
        }
    }
}
