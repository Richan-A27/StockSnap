package com.stocksnap.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.repository.ProductRepository
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

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product
    private val _qrBitmap = MutableStateFlow<android.graphics.Bitmap?>(null)
    val qrBitmap: StateFlow<android.graphics.Bitmap?> = _qrBitmap

    init {
        val id = savedStateHandle.get<Long>("productId") ?: 0L
        if (id > 0L) load(id)
    }

    private fun load(id: Long) {
        viewModelScope.launch {
            val p = repository.getById(id) ?: return@launch
            _product.value = p
            if (p.barcode.isNotEmpty()) {
                try {
                    _qrBitmap.value = QRGenerator.generateBitmap(p.barcode, 256)
                } catch (_: Exception) {
                }
            }
        }
    }

    fun toggleStatus() {
        val p = _product.value ?: return
        val updated = p.copy(
            status = if (p.status == com.stocksnap.domain.model.ProductStatus.PENDING) com.stocksnap.domain.model.ProductStatus.UPDATED else com.stocksnap.domain.model.ProductStatus.PENDING,
            updatedAt = System.currentTimeMillis()
        )
        viewModelScope.launch {
            repository.update(updated)
            _product.value = updated
        }
    }
}
