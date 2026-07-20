package com.stocksnap.presentation.settings

import android.content.Context
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.repository.AuthRepository
import com.stocksnap.data.repository.ProductRepository
import com.stocksnap.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val repository: ProductRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = authRepository.currentUser

    private val _exportStatus = MutableStateFlow<String?>(null)
    val exportStatus: StateFlow<String?> = _exportStatus
    
    val backupStatus: StateFlow<String?> = MutableStateFlow(null)

    fun signOut(context: Context, onSignedOut: () -> Unit) {
        viewModelScope.launch {
            authRepository.signOut()
            val gso = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(context, gso).signOut().addOnCompleteListener {
                onSignedOut()
            }
        }
    }

    private fun checkAdminRole(): Boolean {
        if (currentUser.value?.role != "ADMIN") {
            _exportStatus.value = "Export permission denied."
            return false
        }
        return true
    }

    fun exportProductsCsv(context: Context) {
        if (!checkAdminRole()) return
        viewModelScope.launch {
            try {
                val products = repository.getAllProducts()
                if (products.isEmpty()) {
                    _exportStatus.value = "No products to export"
                    return@launch
                }

                val fileName = "StockSnap_Products_${System.currentTimeMillis()}.csv"
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()
                val file = File(downloadsDir, fileName)
                
                FileWriter(file).use { writer ->
                    writer.append("Barcode,Name,Code,MRP,Status,Quantity,CreatedBy,CreatedAt\n")
                    products.forEach { p ->
                        writer.append("\"${p.barcode}\",\"${p.name}\",\"${p.code}\",${p.mrp},${p.status},${p.quantity},\"N/A\",${p.createdAt}\n")
                    }
                }
                
                repository.logActivityFeed("USER_EXPORTED_PRODUCTS_CSV", "", "Products CSV")
                _exportStatus.value = "Exported to Downloads/${fileName}"
            } catch (e: Exception) {
                _exportStatus.value = "Export failed: ${e.message}"
            }
        }
    }

    fun exportArrivalsCsv(context: Context) {
        if (!checkAdminRole()) return
        viewModelScope.launch {
            try {
                val arrivals = repository.getAllArrivals()
                if (arrivals.isEmpty()) {
                    _exportStatus.value = "No arrivals to export"
                    return@launch
                }

                val fileName = "StockSnap_Arrivals_${System.currentTimeMillis()}.csv"
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()
                val file = File(downloadsDir, fileName)
                
                FileWriter(file).use { writer ->
                    writer.append("ArrivalID,ProductName,Barcode,Code,MRP,QtyReceived,Status,CreatedBy,CreatedAt\n")
                    arrivals.forEach { a ->
                        val product = repository.getProductByBarcode(a.barcode)
                        val codeVal = product?.code ?: ""
                        writer.append("${a.arrivalId},\"${a.productName}\",\"${a.barcode}\",\"${codeVal}\",${a.mrp},${a.quantityReceived},${a.status.name},\"${a.createdByName}\",${a.createdAt}\n")
                    }
                }
                
                repository.logActivityFeed("USER_EXPORTED_ARRIVALS_CSV", "", "Arrivals CSV")
                _exportStatus.value = "Exported to Downloads/${fileName}"
            } catch (e: Exception) {
                _exportStatus.value = "Export failed: ${e.message}"
            }
        }
    }

    fun exportActivityLogsCsv(context: Context) {
        if (!checkAdminRole()) return
        viewModelScope.launch {
            try {
                val activities = repository.getLatestActivityLogs(1000)
                if (activities.isEmpty()) {
                    _exportStatus.value = "No activity logs to export"
                    return@launch
                }

                val fileName = "StockSnap_ActivityLogs_${System.currentTimeMillis()}.csv"
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()
                val file = File(downloadsDir, fileName)
                
                FileWriter(file).use { writer ->
                    writer.append("Timestamp,Action,Details,PerformedBy\n")
                    activities.forEach { a ->
                        writer.append("${a.timestamp},\"${a.actionType}\",\"${a.productName} - ${a.barcode}\",\"${a.performedByName}\"\n")
                    }
                }
                
                repository.logActivityFeed("USER_EXPORTED_ACTIVITY_CSV", "", "Activity Logs CSV")
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
