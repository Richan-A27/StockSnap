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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.presentation.components.GlassListItem
import com.stocksnap.presentation.components.GlassSegmentedControl
import com.stocksnap.presentation.dashboard.StatusChip
import com.stocksnap.ui.theme.AppBackground
import com.stocksnap.ui.theme.PrimaryGreen
import com.stocksnap.ui.theme.TextPrimary
import com.stocksnap.ui.theme.TextSecondary

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onArrivalClick: (String) -> Unit = {}
) {
    val historyState by viewModel.historyState.collectAsState()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        // Ambient glass background blurs
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize().blur(80.dp)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PrimaryGreen.copy(alpha = 0.12f), Color.Transparent)
                ),
                radius = size.width,
                center = Offset(size.width, 0f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) {
            Text(
                text = "Arrival History",
                style = MaterialTheme.typography.displaySmall,
                color = TextPrimary,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            )

            val selectedStatus by viewModel.selectedStatus.collectAsState()
            val selectedDate by viewModel.selectedDate.collectAsState()
            val selectedEmployee by viewModel.selectedEmployee.collectAsState()
            val employeeList by viewModel.employeeList.collectAsState()

            // Filters Section
            Column(modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, bottom = 16.dp)) {
                // Status Segments
                val statuses = listOf("All", "PENDING", "UPDATED")
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
                var expanded by remember { androidx.compose.runtime.mutableStateOf(false) }
                Box {
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

            if (historyState.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = "No history found", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    historyState.forEach { (date, arrivals) ->
                        item {
                            Text(
                                text = date,
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        items(arrivals) { item ->
                            GlassListItem(
                                modifier = Modifier.clickable { onArrivalClick(item.arrival.arrivalId) }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
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
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color.White.copy(alpha = 0.5f)),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    // Product detail column
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.arrival.productName,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary,
                                            maxLines = 1,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (item.product != null && item.product.code.isNotEmpty()) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(4.dp))
                                                        .background(PrimaryGreen.copy(alpha = 0.15f))
                                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                                ) {
                                                    Text(
                                                        text = item.product.code,
                                                        color = PrimaryGreen,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(6.dp))
                                            }
                                            Text(
                                                text = "•  ${item.product?.weight?.ifEmpty { "N/A" } ?: "N/A"}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = TextSecondary,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Qty: ${item.arrival.quantityReceived}   •   MRP: ₹${item.arrival.mrp}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TextSecondary
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "Created by: ${item.arrival.createdByName}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TextSecondary.copy(alpha = 0.8f)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    // Status chip and chevron
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        StatusChip(status = item.arrival.status)
                                        Icon(
                                            imageVector = Icons.Rounded.ChevronRight,
                                            contentDescription = "Open",
                                            tint = TextSecondary.copy(alpha = 0.5f),
                                            modifier = Modifier.size(20.dp)
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
}
