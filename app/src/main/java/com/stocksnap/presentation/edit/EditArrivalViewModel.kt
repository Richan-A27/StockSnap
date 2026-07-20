package com.stocksnap.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.model.Arrival
import com.stocksnap.data.repository.ProductRepository
import com.stocksnap.domain.model.ProductStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditArrivalViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository
) : ViewModel() {

    private val _arrival = MutableStateFlow<Arrival?>(null)
    val arrival: StateFlow<Arrival?> = _arrival

    init {
        val arrivalId = savedStateHandle.get<String>("arrivalId") ?: ""
        if (arrivalId.isNotEmpty()) {
            loadArrival(arrivalId)
        }
    }

    private fun loadArrival(arrivalId: String) {
        viewModelScope.launch {
            _arrival.value = repository.getArrivalByArrivalId(arrivalId)
        }
    }

    fun saveArrival(mrp: Double, quantity: Int, status: ProductStatus, onSaved: () -> Unit) {
        val current = _arrival.value ?: return
        viewModelScope.launch {
            val updated = current.copy(
                mrp = mrp,
                quantityReceived = quantity,
                status = status,
                updatedAt = System.currentTimeMillis()
            )
            repository.updateArrival(updated)
            onSaved()
        }
    }
}
