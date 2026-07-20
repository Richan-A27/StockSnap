package com.stocksnap.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.model.Arrival
import com.stocksnap.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar
import javax.inject.Inject

data class HistoryArrivalItem(
    val arrival: Arrival,
    val product: Product?
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _allArrivals = MutableStateFlow<List<HistoryArrivalItem>>(emptyList())
    
    private val _historyState = MutableStateFlow<Map<String, List<HistoryArrivalItem>>>(emptyMap())
    val historyState: StateFlow<Map<String, List<HistoryArrivalItem>>> = _historyState

    private val _employeeList = MutableStateFlow<List<String>>(listOf("All Staff"))
    val employeeList: StateFlow<List<String>> = _employeeList

    val selectedStatus = MutableStateFlow("All")
    val selectedDate = MutableStateFlow("All Time")
    val selectedEmployee = MutableStateFlow("All Staff")

    init {
        loadHistory()
        
        viewModelScope.launch {
            selectedStatus.collect { applyFilters() }
        }
        viewModelScope.launch {
            selectedDate.collect { applyFilters() }
        }
        viewModelScope.launch {
            selectedEmployee.collect { applyFilters() }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val allArrivals = repository.getAllArrivals()
            val allProducts = repository.getAllProducts()
            val productsMap = allProducts.associateBy { it.barcode }

            val mapped = allArrivals.map { arrival ->
                HistoryArrivalItem(arrival, productsMap[arrival.barcode])
            }
            _allArrivals.value = mapped

            val employees = allArrivals.map { it.createdByName }.distinct().sorted()
            _employeeList.value = listOf("All Staff") + employees

            applyFilters()
        }
    }

    private fun applyFilters() {
        val status = selectedStatus.value
        val dateFilter = selectedDate.value
        val employee = selectedEmployee.value

        var filtered = _allArrivals.value

        // Filter by Status
        if (status != "All") {
            filtered = filtered.filter { it.arrival.status.name.equals(status, ignoreCase = true) }
        }

        // Filter by Employee
        if (employee != "All Staff") {
            filtered = filtered.filter { it.arrival.createdByName == employee }
        }

        // Filter by Date
        filtered = filtered.filter { item ->
            val timestamp = item.arrival.createdAt
            val cal = Calendar.getInstance()
            val now = System.currentTimeMillis()
            
            when (dateFilter) {
                "Today" -> isSameDay(timestamp, now)
                "Yesterday" -> {
                    cal.timeInMillis = now
                    cal.add(Calendar.DAY_OF_YEAR, -1)
                    isSameDay(timestamp, cal.timeInMillis)
                }
                "Last 7 Days" -> timestamp >= now - 7L * 24 * 60 * 60 * 1000
                "This Month" -> {
                    val itemCal = Calendar.getInstance().apply { timeInMillis = timestamp }
                    cal.get(Calendar.MONTH) == itemCal.get(Calendar.MONTH) &&
                    cal.get(Calendar.YEAR) == itemCal.get(Calendar.YEAR)
                }
                else -> true
            }
        }

        val grouped = filtered.groupBy { item ->
            val date = Date(item.arrival.createdAt)
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
        }
        _historyState.value = grouped
    }

    private fun isSameDay(time1: Long, time2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = time1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = time2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    fun setStatus(status: String) { selectedStatus.value = status }
    fun setDate(date: String) { selectedDate.value = date }
    fun setEmployee(employee: String) { selectedEmployee.value = employee }
}
