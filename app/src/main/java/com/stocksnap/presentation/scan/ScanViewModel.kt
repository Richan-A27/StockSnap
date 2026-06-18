package com.stocksnap.presentation.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.domain.usecase.SaveProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val saveProductUseCase: SaveProductUseCase
) : ViewModel() {

    private val _currentProduct = MutableStateFlow<Product?>(null)
    val currentProduct: StateFlow<Product?> = _currentProduct

    fun setCurrentProduct(product: Product) {
        _currentProduct.value = product
    }

    fun processAndSaveProduct(
        context: android.content.Context,
        frontPath: String,
        barcodePath: String,
        mrpPath: String,
        onSaved: (Long) -> Unit
    ) {
        viewModelScope.launch {
            // 1. Create Product
            val now = System.currentTimeMillis()
            val product = Product(
                name = "",
                barcode = "",
                mrp = 0.0,
                brand = null,
                quantity = 1,
                frontImagePath = frontPath,
                barcodeImagePath = barcodePath,
                mrpImagePath = mrpPath,
                thumbnailPath = null,
                status = com.stocksnap.domain.model.ProductStatus.PENDING,
                createdAt = now,
                updatedAt = now
            )
            
            // 3. Save
            val id = saveProductUseCase(product)
            onSaved(id)
        }
    }
}
