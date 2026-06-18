package com.stocksnap.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val exportStatus by viewModel.exportStatus.collectAsState()
    val userAccount by viewModel.userAccount.collectAsState()
    val backupStatus by viewModel.backupStatus.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkAccount(context)
    }

    val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(com.google.android.gms.common.api.ApiException::class.java)
            viewModel.handleSignInResult(account)
            viewModel.updateBackupStatus("Signed in as ${account?.email}")
        } catch (e: Exception) {
            e.printStackTrace()
            viewModel.updateBackupStatus("Sign-in error: ${e.message}")
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF6F6F6)).padding(16.dp)) {
        Text(
            text = "Settings", 
            style = MaterialTheme.typography.headlineLarge, 
            fontWeight = FontWeight.Bold, 
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Account Section
        Text(text = "Backup & Restore", style = MaterialTheme.typography.titleSmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(android.R.drawable.ic_menu_myplaces), contentDescription = null, tint = Color(0xFF006E3E))
                    Spacer(Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Account", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = userAccount?.email ?: "Not signed in", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                    if (userAccount == null) {
                        Button(
                            onClick = { launcher.launch(viewModel.driveService.getGoogleSignInClient().signInIntent) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Sign in with Google")
                        }
                    } else {
                        TextButton(onClick = { viewModel.signOut(context) }) {
                            Text("Sign Out", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }

                Divider(modifier = Modifier.padding(horizontal = 16.dp))

                ActionRow(label = "Backup to Drive", description = "Back up your products, images and data", onClick = { viewModel.createBackup() }, enabled = userAccount != null)
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                ActionRow(label = "Restore from Drive", description = "Restore your data from latest backup", onClick = { viewModel.restoreBackup() }, enabled = userAccount != null)
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                ActionRow(label = "Export as CSV", description = "Save your data as a local file", onClick = { viewModel.exportToCsv(context) }, enabled = true)
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

        Spacer(modifier = Modifier.height(24.dp))

        // Info Section
        Text(text = "About", style = MaterialTheme.typography.titleSmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "App Version", style = MaterialTheme.typography.bodyLarge)
                Text(text = "StockSnap v1.1", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "StockSnap • 2026",
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
            painter = painterResource(android.R.drawable.ic_media_play),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = if (enabled) Color(0xFF006E3E) else Color.Gray
        )
    }
}
