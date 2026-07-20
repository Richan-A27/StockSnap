package com.stocksnap.presentation.admin

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.data.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserManagementScreen(
    onBack: () -> Unit,
    viewModel: UserManagementViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val pendingUsers by viewModel.pendingUsers.collectAsState()
    val approvedUsers by viewModel.approvedUsers.collectAsState()
    val disabledUsers by viewModel.disabledUsers.collectAsState()
    val arrivalsCountMap by viewModel.userArrivalsCount.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
    ) {
        // Top Bar
        TopAppBar(
            title = { Text("Manage Employees", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                placeholder = { Text("Search by name or email...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF006E3E),
                    unfocusedBorderColor = Color.LightGray
                )
            )
        }

        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // PENDING APPROVAL SECTION
            if (pendingUsers.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Pending Approval",
                        count = pendingUsers.size,
                        color = Color(0xFFFFA726)
                    )
                }
                items(pendingUsers) { user ->
                    PendingUserCard(
                        user = user,
                        onApprove = { viewModel.approveUser(user) },
                        onReject = { viewModel.rejectUser(user) }
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }

            // APPROVED EMPLOYEES SECTION
            if (approvedUsers.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Approved Employees",
                        count = approvedUsers.size,
                        color = Color(0xFF006E3E)
                    )
                }
                items(approvedUsers) { user ->
                    val count = arrivalsCountMap[user.uid] ?: 0
                    ApprovedUserCard(
                        user = user,
                        count = count,
                        isSelf = user.uid == currentUser?.uid,
                        onDisable = { viewModel.disableUser(user) }
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }

            // DISABLED EMPLOYEES SECTION
            if (disabledUsers.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Disabled / Rejected",
                        count = disabledUsers.size,
                        color = Color(0xFF78909C)
                    )
                }
                items(disabledUsers) { user ->
                    DisabledUserCard(
                        user = user,
                        onEnable = { viewModel.enableUser(user) }
                    )
                }
            }

            // Empty state
            if (pendingUsers.isEmpty() && approvedUsers.isEmpty() && disabledUsers.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No employees found.", color = Color.Gray)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun SectionHeader(title: String, count: Int, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = color.copy(alpha = 0.15f),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = "$count",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun PendingUserCard(
    user: User,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    val context = LocalContext.current
    val sdf = remember { SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault()) }
    val formattedLogin = if (user.lastLogin > 0L) sdf.format(Date(user.lastLogin)) else "—"

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(user.photoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = user.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(text = user.email, fontSize = 12.sp, color = Color.Gray)
                    Text(text = "Login: $formattedLogin", fontSize = 11.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Approve / Reject buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onApprove,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E))
                ) {
                    Text("Approve", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                OutlinedButton(
                    onClick = onReject,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD32F2F))
                ) {
                    Text("Reject", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun ApprovedUserCard(
    user: User,
    count: Int,
    isSelf: Boolean,
    onDisable: () -> Unit
) {
    val context = LocalContext.current
    val sdf = remember { SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault()) }
    val formattedLogin = if (user.lastLogin > 0L) sdf.format(Date(user.lastLogin)) else "Never"

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(user.photoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = user.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        RoleBadge(role = user.role)
                    }
                    Text(text = user.email, fontSize = 12.sp, color = Color.Gray)
                }

                if (!isSelf) {
                    TextButton(
                        onClick = onDisable,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFD32F2F))
                    ) {
                        Text("Disable", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color(0xFFEEEEEE))
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Deliveries: $count",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    text = "Last Login: $formattedLogin",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DisabledUserCard(
    user: User,
    onEnable: () -> Unit
) {
    val context = LocalContext.current
    val statusText = if (user.approvalStatus == "REJECTED") "Rejected" else "Disabled"
    val statusColor = if (user.approvalStatus == "REJECTED") Color(0xFFD32F2F) else Color(0xFF78909C)

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFFAFAFA))
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
                contentDescription = "Profile",
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = user.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = statusColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = statusText,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                        )
                    }
                }
                Text(text = user.email, fontSize = 11.sp, color = Color.Gray)
            }

            Button(
                onClick = onEnable,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E)),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text("Enable", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun RoleBadge(role: String) {
    val isAdmin = role == "ADMIN"
    val bgColor = if (isAdmin) Color(0xFFE8F5E9) else Color(0xFFEEEEEE)
    val textColor = if (isAdmin) Color(0xFF2E7D32) else Color(0xFF616161)

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(horizontal = 2.dp)
    ) {
        Text(
            text = role,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
