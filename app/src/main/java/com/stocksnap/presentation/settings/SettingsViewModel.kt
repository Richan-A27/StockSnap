package com.stocksnap.presentation.settings

import android.content.Context
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: com.stocksnap.data.repository.ProductRepository,
    private val backupManager: com.stocksnap.backup.BackupManager,
    private val restoreManager: com.stocksnap.backup.RestoreManager,
    val driveService: com.stocksnap.backup.DriveService
) : ViewModel() {

    private val _userAccount = MutableStateFlow<com.google.android.gms.auth.api.signin.GoogleSignInAccount?>(null)
    val userAccount: StateFlow<com.google.android.gms.auth.api.signin.GoogleSignInAccount?> = _userAccount

    private val _backupStatus = MutableStateFlow<String?>(null)
    val backupStatus: StateFlow<String?> = _backupStatus

    fun checkAccount(context: android.content.Context) {
        _userAccount.value = com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(context)
    }

    fun handleSignInResult(account: com.google.android.gms.auth.api.signin.GoogleSignInAccount?) {
        _userAccount.value = account
    }

    fun signOut(context: android.content.Context) {
        viewModelScope.launch {
            driveService.getGoogleSignInClient().signOut().addOnCompleteListener {
                _userAccount.value = null
            }
        }
    }

    fun createBackup() {
        val account = _userAccount.value ?: return
        _backupStatus.value = "Creating backup..."
        viewModelScope.launch {
            val result = backupManager.createAndUploadBackup(account)
            _backupStatus.value = if (result.isSuccess) "Backup uploaded successfully!" else "Backup failed: ${result.exceptionOrNull()?.message}"
        }
    }

    fun restoreBackup() {
        val account = _userAccount.value ?: return
        _backupStatus.value = "Restoring backup..."
        viewModelScope.launch {
            val result = restoreManager.downloadAndRestore(account)
            _backupStatus.value = if (result.isSuccess) "Restore complete! Please restart the app." else "Restore failed: ${result.exceptionOrNull()?.message}"
        }
    }

    fun updateBackupStatus(status: String) {
        _backupStatus.value = status
    }

    private val _exportStatus = MutableStateFlow<String?>(null)
    val exportStatus: StateFlow<String?> = _exportStatus

    fun exportToCsv(context: Context) {
        viewModelScope.launch {
            try {
                val products = repository.getAll()
                if (products.isEmpty()) {
                    _exportStatus.value = "No products to export"
                    return@launch
                }

                val fileName = "StockSnap_Export_${System.currentTimeMillis()}.csv"
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()
                val file = File(downloadsDir, fileName)
                
                FileWriter(file).use { writer ->
                    writer.append("ID,Name,Barcode,MRP,Brand,Quantity,Status,CreatedAt\n")
                    products.forEach { p ->
                        writer.append("${p.id},\"${p.name}\",\"${p.barcode}\",${p.mrp},\"${p.brand ?: ""}\",${p.quantity ?: 1},${p.status.name},${p.createdAt}\n")
                    }
                }
                
                _exportStatus.value = "Exported to Downloads/${fileName}"
            } catch (e: Exception) {
                _exportStatus.value = "Export failed: ${e.message}"
            }
        }
    }

    fun clearStatus() {
        _exportStatus.value = null
    }
}
