package com.stocksnap.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            val list = repository.getAllProducts()
            _allProducts.value = list
            filterProducts()
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        filterProducts()
    }

    private fun filterProducts() {
        val query = _searchQuery.value.trim().lowercase(Locale.getDefault())
        if (query.isEmpty()) {
            _products.value = _allProducts.value
        } else {
            _products.value = _allProducts.value.filter {
                it.name.lowercase(Locale.getDefault()).contains(query) ||
                it.barcode.lowercase(Locale.getDefault()).contains(query) ||
                it.code.lowercase(Locale.getDefault()).contains(query)
            }
        }
    }
}
