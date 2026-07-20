package com.stocksnap.presentation.edit

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.repository.ProductRepository
import com.stocksnap.camera.ThumbnailUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    init {
        val id = savedStateHandle.get<Long>("productId") ?: 0L
        if (id > 0L) {
            loadProduct(id)
        }
    }

    private fun loadProduct(id: Long) {
        viewModelScope.launch {
            _product.value = repository.getProductById(id)
        }
    }

    fun saveProduct(
        context: Context,
        name: String,
        weight: String,
        code: String,
        newFrontImagePath: String?,
        onSaved: () -> Unit
    ) {
        val p = _product.value ?: return
        viewModelScope.launch {
            var updated = p.copy(
                name = name,
                weight = weight,
                code = code,
                updatedAt = System.currentTimeMillis()
            )

            if (!newFrontImagePath.isNullOrEmpty()) {
                if (updated.frontImagePath.isNotEmpty()) {
                    try { File(updated.frontImagePath).delete() } catch (_: Exception) {}
                }
                val existingThumb = updated.thumbnailPath
                if (!existingThumb.isNullOrEmpty()) {
                    try { File(existingThumb).delete() } catch (_: Exception) {}
                }

                val thumb = ThumbnailUtils.generateThumbnail(context, newFrontImagePath)
                updated = updated.copy(
                    frontImagePath = newFrontImagePath,
                    thumbnailPath = thumb,
                    frontImageUrl = null
                )
            }

            repository.updateProduct(updated)
            onSaved()
        }
    }
}
