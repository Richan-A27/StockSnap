package com.stocksnap.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.model.ActivityLog
import com.stocksnap.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ActivityLogsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _allLogs = MutableStateFlow<List<ActivityLog>>(emptyList())
    
    private val _filteredLogs = MutableStateFlow<List<ActivityLog>>(emptyList())
    val filteredLogs: StateFlow<List<ActivityLog>> = _filteredLogs

    private val _employeeList = MutableStateFlow<List<String>>(listOf("All Staff"))
    val employeeList: StateFlow<List<String>> = _employeeList

    val selectedStatus = MutableStateFlow("All")
    val selectedDate = MutableStateFlow("All Time")
    val selectedEmployee = MutableStateFlow("All Staff")
    val searchQuery = MutableStateFlow("")

    init {
        loadLogs()
        
        viewModelScope.launch { selectedStatus.collect { applyFilters() } }
        viewModelScope.launch { selectedDate.collect { applyFilters() } }
        viewModelScope.launch { selectedEmployee.collect { applyFilters() } }
        viewModelScope.launch { searchQuery.collect { applyFilters() } }
    }

    private fun loadLogs() {
        viewModelScope.launch {
            val logs = repository.getLatestActivityLogs(500)
            _allLogs.value = logs
            
            val employees = logs.map { it.performedByName }.distinct().sorted()
            _employeeList.value = listOf("All Staff") + employees

            applyFilters()
        }
    }

    private fun applyFilters() {
        val status = selectedStatus.value
        val dateFilter = selectedDate.value
        val employee = selectedEmployee.value
        val query = searchQuery.value.lowercase()

        var filtered = _allLogs.value

        // Filter by Status (Action Type mapping)
        if (status != "All") {
            filtered = filtered.filter { log ->
                val type = log.actionType
                when (status) {
                    "Created" -> type.contains("CREATED") || type.contains("ADD")
                    "Updated" -> type.contains("UPDATED") || type.contains("CHANGED") || type.contains("PENDING")
                    "Approved" -> type.contains("APPROVED") || type.contains("ENABLED")
                    "Disabled" -> type.contains("DISABLED") || type.contains("REJECTED")
                    else -> true
                }
            }
        }

        // Filter by Employee
        if (employee != "All Staff") {
            filtered = filtered.filter { it.performedByName == employee }
        }

        // Filter by Search Query
        if (query.isNotEmpty()) {
            filtered = filtered.filter { log ->
                log.performedByName.lowercase().contains(query) ||
                log.productName.lowercase().contains(query) ||
                log.barcode.lowercase().contains(query) ||
                log.actionType.lowercase().contains(query)
            }
        }

        // Filter by Date
        filtered = filtered.filter { log ->
            val timestamp = log.timestamp
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

        _filteredLogs.value = filtered
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
    fun setSearchQuery(q: String) { searchQuery.value = q }
}
