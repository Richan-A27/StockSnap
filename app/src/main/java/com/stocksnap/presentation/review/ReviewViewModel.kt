package com.stocksnap.presentation.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.repository.ProductRepository
import com.stocksnap.ocr.MLKitProcessor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository,
    private val mlKitProcessor: MLKitProcessor
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product
    private val _previousMrp = MutableStateFlow<Double?>(null)
    val previousMrp: StateFlow<Double?> = _previousMrp

    init {
        val id = savedStateHandle.get<Long>("productId") ?: 0L
        if (id > 0L) loadProduct(id)
    }

    private fun loadProduct(id: Long) {
        viewModelScope.launch {
            val p = repository.getById(id) ?: return@launch
            // process images with ML Kit to extract fields
            val processed = try {
                mlKitProcessor.processProductImages(p)
            } catch (e: Exception) {
                p
            }
            _product.value = processed
            // detect previous MRP for same barcode (if any)
            if (processed.barcode.isNotEmpty()) {
                val existing = repository.findByBarcode(processed.barcode)
                if (existing != null && existing.id != processed.id) {
                    _previousMrp.value = existing.mrp
                }
            }
        }
    }

    fun updateProduct(updated: Product) {
        viewModelScope.launch {
            repository.update(updated)
            _product.value = updated
        }
    }
}
