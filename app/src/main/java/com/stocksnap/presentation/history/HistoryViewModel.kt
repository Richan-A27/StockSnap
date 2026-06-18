package com.stocksnap.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _historyState = MutableStateFlow<Map<String, List<Product>>>(emptyMap())
    val historyState: StateFlow<Map<String, List<Product>>> = _historyState

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val allProducts = repository.getAll()
            val grouped = allProducts.groupBy { 
                val date = Date(it.createdAt)
                SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
            }
            _historyState.value = grouped
        }
    }
}
