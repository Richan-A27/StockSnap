package com.stocksnap.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Refresh

/**
 * Unified screen for PENDING / REJECTED / DISABLED account states.
 * Shows appropriate message and available actions per state.
 */
@Composable
fun AccountStatusScreen(
    statusType: String, // "PENDING", "REJECTED", "DISABLED"
    onLogout: () -> Unit,
    onRefresh: () -> Unit = {}
) {
    var refreshing by remember { mutableStateOf(false) }

    val (title, message, iconVector, accentColor) = when (statusType) {
        "PENDING" -> AccountInfo(
            title = "Awaiting Approval",
            message = "Your account is awaiting administrator approval.\n\nPlease check back later or contact your store administrator.",
            iconVector = Icons.Rounded.HourglassTop,
            accentColor = Color(0xFFFFA726)
        )
        "REJECTED" -> AccountInfo(
            title = "Access Denied",
            message = "Your access request was rejected.\n\nPlease contact the store administrator for more information.",
            iconVector = Icons.Rounded.Block,
            accentColor = Color(0xFFEF5350)
        )
        else -> AccountInfo( // DISABLED
            title = "Account Disabled",
            message = "Your account has been disabled.\n\nContact your administrator to restore access.",
            iconVector = Icons.Rounded.Lock,
            accentColor = Color(0xFF78909C)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF004D2C), Color(0xFF001B0E))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Status Icon
            Surface(
                modifier = Modifier.size(96.dp),
                shape = RoundedCornerShape(24.dp),
                color = accentColor.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = title,
                        tint = accentColor,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = message,
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Refresh Status button (only for PENDING)
            if (statusType == "PENDING") {
                Button(
                    onClick = {
                        refreshing = true
                        onRefresh()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    if (refreshing) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Refresh Status",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Logout button
            OutlinedButton(
                onClick = onLogout,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Sign Out",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "StockSnap • Authorized Personnel Only",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.3f)
            )
        }
    }
}

private data class AccountInfo(
    val title: String,
    val message: String,
    val iconVector: ImageVector,
    val accentColor: Color
)
