package com.stocksnap.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight

@Composable
fun SettingsScreen(
    onNavigateToUserManagement: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val exportStatus by viewModel.exportStatus.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val backupStatus by viewModel.backupStatus.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Settings", 
            style = MaterialTheme.typography.headlineLarge, 
            fontWeight = FontWeight.Bold, 
            modifier = Modifier.padding(bottom = 20.dp)
        )
        
        // Employee Account Card
        currentUser?.let { user ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(user.photoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = user.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = user.email, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        Text(text = "Role: ${user.role}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = Color(0xFF006E3E))
                    }
                    TextButton(onClick = { viewModel.signOut(context) {} }) {
                        Text("Sign Out", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }

        // Account Status Card
        currentUser?.let { user ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Account Status",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Role
                    StatusRow(
                        label = "Role",
                        value = user.role,
                        color = if (user.role == "ADMIN") Color(0xFF2E7D32) else Color(0xFF1565C0)
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFF0F0F0))

                    // Approval Status
                    val approvalColor = when (user.approvalStatus) {
                        "APPROVED" -> Color(0xFF2E7D32)
                        "REJECTED" -> Color(0xFFD32F2F)
                        else -> Color(0xFFFFA726)
                    }
                    StatusRow(
                        label = "Approval Status",
                        value = user.approvalStatus,
                        color = approvalColor
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFF0F0F0))

                    // Access Status
                    val accessText = if (user.active) "ACTIVE" else "DISABLED"
                    val accessColor = if (user.active) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                    StatusRow(
                        label = "Access Status",
                        value = accessText,
                        color = accessColor
                    )
                }
            }
        }

        // Admin Exports Section
        if (currentUser?.role == "ADMIN") {
            Text(text = "Local Exports", style = MaterialTheme.typography.titleSmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column {
                    ActionRow(
                        label = "Export Products CSV", 
                        description = "Save all products locally", 
                        onClick = { viewModel.exportProductsCsv(context) }, 
                        enabled = true
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    ActionRow(
                        label = "Export Arrivals CSV", 
                        description = "Save all arrivals deliveries locally", 
                        onClick = { viewModel.exportArrivalsCsv(context) }, 
                        enabled = true
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    ActionRow(
                        label = "Export Activity Logs CSV", 
                        description = "Save all activity logs locally", 
                        onClick = { viewModel.exportActivityLogsCsv(context) }, 
                        enabled = true
                    )
                }
            }
        }

        if (backupStatus != null || exportStatus != null) {
            Text(
                text = backupStatus ?: exportStatus ?: "",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp),
                color = Color(0xFF006E3E),
                fontWeight = FontWeight.Bold
            )
        }

        // Admin Tools Section
        if (currentUser?.role == "ADMIN") {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Admin Panel", style = MaterialTheme.typography.titleSmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                ActionRow(
                    label = "Manage Employees", 
                    description = "Search, enable, or disable employee accounts", 
                    onClick = onNavigateToUserManagement, 
                    enabled = true
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Info Section
        Text(text = "About", style = MaterialTheme.typography.titleSmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "App Version", style = MaterialTheme.typography.bodyLarge)
                Text(text = "StockSnap Collaborative v2.0", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "StockSnap Collaborative • 2026",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray
        )
    }
}

@Composable
fun ActionRow(label: String, description: String, onClick: () -> Unit, enabled: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = label, style = MaterialTheme.typography.titleMedium, color = if (enabled) Color.Black else Color.Gray)
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = if (enabled) Color(0xFF006E3E) else Color.Gray
        )
    }
}

@Composable
fun StatusRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Surface(
            color = color.copy(alpha = 0.12f),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = value,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}
