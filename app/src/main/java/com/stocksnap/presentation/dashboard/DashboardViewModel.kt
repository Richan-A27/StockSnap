package com.stocksnap.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<Product>>(emptyList())
    val items: StateFlow<List<Product>> = _items
    private val _total = MutableStateFlow(0)
    val total: StateFlow<Int> = _total
    private val _updated = MutableStateFlow(0)
    val updated: StateFlow<Int> = _updated
    private val _pending = MutableStateFlow(0)
    val pending: StateFlow<Int> = _pending

    init {
        loadToday()
    }

    fun loadToday() {
        viewModelScope.launch {
            val list = repository.getByDay(System.currentTimeMillis())
            _items.value = list
            _total.value = list.size
            _updated.value = list.count { it.status == com.stocksnap.domain.model.ProductStatus.UPDATED }
            _pending.value = list.count { it.status == com.stocksnap.domain.model.ProductStatus.PENDING }
        }
    }

    fun toggleStatus(product: Product) {
        viewModelScope.launch {
            repository.update(product.copy(status = com.stocksnap.domain.model.ProductStatus.UPDATED, updatedAt = System.currentTimeMillis()))
            loadToday()
        }
    }
}
