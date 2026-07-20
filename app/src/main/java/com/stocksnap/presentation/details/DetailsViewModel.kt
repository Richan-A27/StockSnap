package com.stocksnap.presentation.details

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.model.Arrival
import com.stocksnap.data.repository.ProductRepository
import com.stocksnap.domain.model.ProductStatus
import com.stocksnap.qr.QRGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository
) : ViewModel() {

    private val _arrival = MutableStateFlow<Arrival?>(null)
    val arrival: StateFlow<Arrival?> = _arrival

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _qrBitmap = MutableStateFlow<Bitmap?>(null)
    val qrBitmap: StateFlow<Bitmap?> = _qrBitmap

    init {
        val arrivalId = savedStateHandle.get<String>("arrivalId") ?: ""
        if (arrivalId.isNotEmpty()) {
            load(arrivalId)
        }
    }

    private fun load(arrivalId: String) {
        viewModelScope.launch {
            val arr = repository.getArrivalByArrivalId(arrivalId) ?: return@launch
            _arrival.value = arr
            
            val prod = repository.getProductByBarcode(arr.barcode)
            _product.value = prod
            
            if (arr.barcode.isNotEmpty()) {
                try {
                    _qrBitmap.value = QRGenerator.generateBitmap(arr.barcode, 256)
                } catch (_: Exception) {}
            }
        }
    }

    fun toggleProductStatus() {
        val arr = _arrival.value ?: return
        val updated = arr.copy(
            status = if (arr.status == ProductStatus.PENDING) ProductStatus.UPDATED else ProductStatus.PENDING,
            updatedAt = System.currentTimeMillis()
        )
        viewModelScope.launch {
            repository.updateArrival(updated)
            _arrival.value = updated
        }
    }

    fun deleteArrival(onDeleted: () -> Unit) {
        val arr = _arrival.value ?: return
        viewModelScope.launch {
            repository.deleteArrival(arr.id)
            onDeleted()
        }
    }
}
