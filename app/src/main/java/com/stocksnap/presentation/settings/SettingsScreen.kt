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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import com.stocksnap.presentation.components.GlassButton
import com.stocksnap.presentation.components.GlassCard
import com.stocksnap.ui.theme.AppBackground
import com.stocksnap.ui.theme.PrimaryGreen
import com.stocksnap.ui.theme.StatusDanger
import com.stocksnap.ui.theme.StatusWarning
import com.stocksnap.ui.theme.TextPrimary
import com.stocksnap.ui.theme.TextSecondary

@Composable
fun SettingsScreen(
    onNavigateToUserManagement: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val exportStatus by viewModel.exportStatus.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val backupStatus by viewModel.backupStatus.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        // Ambient glass background blurs
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize().blur(80.dp)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PrimaryGreen.copy(alpha = 0.12f), Color.Transparent)
                ),
                radius = size.width,
                center = Offset(size.width * 0.5f, 0f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "Settings", 
                style = MaterialTheme.typography.displaySmall, 
                color = TextPrimary, 
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
            )
            
            // Employee Account Card
            currentUser?.let { user ->
                GlassCard(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(user.photoUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.5f)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = user.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text(text = user.email, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Role: ${user.role}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = PrimaryGreen)
                            }
                        }
                        Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)) {
                            GlassButton(
                                text = "Sign Out",
                                onClick = { viewModel.signOut(context) {} },
                                containerColor = StatusDanger.copy(alpha = 0.1f),
                                contentColor = StatusDanger
                            )
                        }
                    }
                }
            }

            // Account Status Card
            currentUser?.let { user ->
                GlassCard(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Account Status",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Role
                        StatusRow(
                            label = "Role",
                            value = user.role,
                            color = if (user.role == "ADMIN") PrimaryGreen else Color(0xFF1565C0)
                        )

                        Divider(modifier = Modifier.padding(vertical = 12.dp), color = TextSecondary.copy(alpha = 0.1f))

                        // Approval Status
                        val approvalColor = when (user.approvalStatus) {
                            "APPROVED" -> PrimaryGreen
                            "REJECTED" -> StatusDanger
                            else -> StatusWarning
                        }
                        StatusRow(
                            label = "Approval Status",
                            value = user.approvalStatus,
                            color = approvalColor
                        )

                        Divider(modifier = Modifier.padding(vertical = 12.dp), color = TextSecondary.copy(alpha = 0.1f))

                        // Access Status
                        val accessText = if (user.active) "ACTIVE" else "DISABLED"
                        val accessColor = if (user.active) PrimaryGreen else StatusDanger
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
                Text(text = "Local Exports", style = MaterialTheme.typography.labelLarge, color = TextSecondary, modifier = Modifier.padding(bottom = 12.dp, start = 4.dp))
                GlassCard(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                ) {
                    Column {
                        ActionRow(
                            label = "Export Products CSV", 
                            description = "Save all products locally", 
                            onClick = { viewModel.exportProductsCsv(context) }, 
                            enabled = true
                        )
                        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = TextSecondary.copy(alpha = 0.1f))
                        ActionRow(
                            label = "Export Arrivals CSV", 
                            description = "Save all arrivals deliveries locally", 
                            onClick = { viewModel.exportArrivalsCsv(context) }, 
                            enabled = true
                        )
                        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = TextSecondary.copy(alpha = 0.1f))
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
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                    color = PrimaryGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            // Admin Tools Section
            if (currentUser?.role == "ADMIN") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Admin Panel", style = MaterialTheme.typography.labelLarge, color = TextSecondary, modifier = Modifier.padding(bottom = 12.dp, start = 4.dp))
                GlassCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ActionRow(
                        label = "Manage Employees", 
                        description = "Search, enable, or disable employee accounts", 
                        onClick = onNavigateToUserManagement, 
                        enabled = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Info Section
            Text(text = "About", style = MaterialTheme.typography.labelLarge, color = TextSecondary, modifier = Modifier.padding(bottom = 12.dp, start = 4.dp))
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "App Version", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                    Text(text = "StockSnap Collaborative v2.0", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "StockSnap Collaborative • 2026",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 100.dp),
                color = TextSecondary
            )
        }
    }
}

@Composable
fun ActionRow(label: String, description: String, onClick: () -> Unit, enabled: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick)
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = label, style = MaterialTheme.typography.titleMedium, color = if (enabled) TextPrimary else TextSecondary)
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (enabled) PrimaryGreen else TextSecondary.copy(alpha = 0.5f)
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
            color = TextSecondary
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color.copy(alpha = 0.15f))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}
