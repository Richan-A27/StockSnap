package com.stocksnap.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.model.ActivityLog
import com.stocksnap.data.model.Arrival
import com.stocksnap.data.repository.ProductRepository
import com.stocksnap.domain.model.ProductStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class DashboardArrivalItem(
    val arrival: Arrival,
    val product: Product?
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val authRepository: com.stocksnap.data.repository.AuthRepository
) : ViewModel() {

    val currentUser = authRepository.currentUser

    private val _allItems = MutableStateFlow<List<Arrival>>(emptyList())
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())

    private val _items = MutableStateFlow<List<DashboardArrivalItem>>(emptyList())
    val items: StateFlow<List<DashboardArrivalItem>> = _items

    private val _activityLogs = MutableStateFlow<List<ActivityLog>>(emptyList())
    val activityLogs: StateFlow<List<ActivityLog>> = _activityLogs

    private val _total = MutableStateFlow(0)
    val total: StateFlow<Int> = _total
    private val _updated = MutableStateFlow(0)
    val updated: StateFlow<Int> = _updated
    private val _pending = MutableStateFlow(0)
    val pending: StateFlow<Int> = _pending

    // Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Filter states
    private val _selectedStatus = MutableStateFlow("All")
    val selectedStatus: StateFlow<String> = _selectedStatus

    private val _selectedDate = MutableStateFlow("Today")
    val selectedDate: StateFlow<String> = _selectedDate

    private val _selectedEmployee = MutableStateFlow("All Employees")
    val selectedEmployee: StateFlow<String> = _selectedEmployee

    private val _employeeList = MutableStateFlow<List<String>>(emptyList())
    val employeeList: StateFlow<List<String>> = _employeeList

    init {
        loadToday()
    }

    fun loadToday() {
        viewModelScope.launch {
            val arrivals = repository.getAllArrivals()
            _allItems.value = arrivals
            
            val products = repository.getAllProducts()
            _allProducts.value = products

            // Extract unique employee names
            val employees = arrivals.map { it.createdByName }.distinct().sorted()
            _employeeList.value = employees

            applyFilters()

            // Fetch shared activity feed (from all employees, limit 100)
            _activityLogs.value = repository.getLatestActivityLogs(100)
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    fun setStatusFilter(status: String) {
        _selectedStatus.value = status
        applyFilters()
    }

    fun setDateFilter(date: String) {
        _selectedDate.value = date
        applyFilters()
    }

    fun setEmployeeFilter(employee: String) {
        _selectedEmployee.value = employee
        applyFilters()
    }

    private fun applyFilters() {
        val arrivals = _allItems.value
        val productsMap = _allProducts.value.associateBy { it.barcode }
        
        val statusFilter = _selectedStatus.value
        val dateFilter = _selectedDate.value
        val employeeFilter = _selectedEmployee.value
        val query = _searchQuery.value.trim().lowercase(Locale.getDefault())

        val filtered = arrivals.filter { arrival ->
            // 1. Status Filter
            val matchesStatus = when (statusFilter) {
                "Pending" -> arrival.status == ProductStatus.PENDING
                "Updated" -> arrival.status == ProductStatus.UPDATED
                else -> true
            }

            // 2. Date Filter
            val matchesDate = when (dateFilter) {
                "Today" -> isSameLocalDate(arrival.createdAt, System.currentTimeMillis())
                "Yesterday" -> isYesterday(arrival.createdAt)
                "Last 7 Days" -> isWithinLast7Days(arrival.createdAt)
                else -> true
            }

            // 3. Employee Filter
            val matchesEmployee = if (employeeFilter == "All Employees") {
                true
            } else {
                arrival.createdByName == employeeFilter
            }

            matchesStatus && matchesDate && matchesEmployee
        }

        // Map to combined items and search filter
        val mappedItems = filtered.map { arrival ->
            DashboardArrivalItem(arrival, productsMap[arrival.barcode])
        }.filter { item ->
            if (query.isEmpty()) {
                true
            } else {
                val matchesBarcode = item.arrival.barcode.lowercase(Locale.getDefault()).contains(query)
                val matchesName = item.arrival.productName.lowercase(Locale.getDefault()).contains(query)
                val matchesCode = item.product?.code?.lowercase(Locale.getDefault())?.contains(query) ?: false
                matchesBarcode || matchesName || matchesCode
            }
        }

        _items.value = mappedItems
        
        // Stats update based on general date/employee filter
        _total.value = filtered.size
        _updated.value = filtered.count { it.status == ProductStatus.UPDATED }
        _pending.value = filtered.count { it.status == ProductStatus.PENDING }
    }

    private fun isSameLocalDate(t1: Long, t2: Long): Boolean {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return sdf.format(Date(t1)) == sdf.format(Date(t2))
    }

    private fun isYesterday(t: Long): Boolean {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val dateStr = sdf.format(Date(t))
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterdayStr = sdf.format(cal.time)
        return dateStr == yesterdayStr
    }

    private fun isWithinLast7Days(t: Long): Boolean {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -7)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return t >= cal.timeInMillis
    }
}
