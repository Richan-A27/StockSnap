package com.stocksnap.presentation.dashboard;

import androidx.lifecycle.ViewModel;
import com.stocksnap.data.database.Product;
import com.stocksnap.data.model.ActivityLog;
import com.stocksnap.data.model.Arrival;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.domain.model.ProductStatus;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u00104\u001a\u000205H\u0002J\u0018\u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209H\u0002J\u0010\u0010;\u001a\u0002072\u0006\u0010<\u001a\u000209H\u0002J\u0010\u0010=\u001a\u0002072\u0006\u0010<\u001a\u000209H\u0002J\u0006\u0010>\u001a\u000205J\u000e\u0010?\u001a\u0002052\u0006\u0010@\u001a\u00020\u0010J\u000e\u0010A\u001a\u0002052\u0006\u0010B\u001a\u00020\u0010J\u000e\u0010C\u001a\u0002052\u0006\u0010D\u001a\u00020\u0010J\u000e\u0010E\u001a\u0002052\u0006\u0010F\u001a\u00020\u0010R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00100\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00140\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00140\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010 0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001eR\u001d\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\t0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001eR\u001d\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\t0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001eR\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00140\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00100\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00100\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001eR\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00100\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001eR\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00100\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001eR\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\u00140\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001eR\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020\u00140\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\u001e\u00a8\u0006G"}, d2 = {"Lcom/stocksnap/presentation/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "authRepository", "Lcom/stocksnap/data/repository/AuthRepository;", "(Lcom/stocksnap/data/repository/ProductRepository;Lcom/stocksnap/data/repository/AuthRepository;)V", "_activityLogs", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/stocksnap/data/model/ActivityLog;", "_allItems", "Lcom/stocksnap/data/model/Arrival;", "_allProducts", "Lcom/stocksnap/data/database/Product;", "_employeeList", "", "_items", "Lcom/stocksnap/presentation/dashboard/DashboardArrivalItem;", "_pending", "", "_searchQuery", "_selectedDate", "_selectedEmployee", "_selectedStatus", "_total", "_updated", "activityLogs", "Lkotlinx/coroutines/flow/StateFlow;", "getActivityLogs", "()Lkotlinx/coroutines/flow/StateFlow;", "currentUser", "Lcom/stocksnap/data/model/User;", "getCurrentUser", "employeeList", "getEmployeeList", "items", "getItems", "pending", "getPending", "searchQuery", "getSearchQuery", "selectedDate", "getSelectedDate", "selectedEmployee", "getSelectedEmployee", "selectedStatus", "getSelectedStatus", "total", "getTotal", "updated", "getUpdated", "applyFilters", "", "isSameLocalDate", "", "t1", "", "t2", "isWithinLast7Days", "t", "isYesterday", "loadToday", "setDateFilter", "date", "setEmployeeFilter", "employee", "setSearchQuery", "query", "setStatusFilter", "status", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.model.User> currentUser = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.stocksnap.data.model.Arrival>> _allItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.stocksnap.data.database.Product>> _allProducts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.stocksnap.presentation.dashboard.DashboardArrivalItem>> _items = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.presentation.dashboard.DashboardArrivalItem>> items = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.stocksnap.data.model.ActivityLog>> _activityLogs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.ActivityLog>> activityLogs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _total = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> total = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _updated = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> updated = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _pending = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> pending = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedEmployee = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedEmployee = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _employeeList = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> employeeList = null;
    
    @javax.inject.Inject()
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository, @org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.model.User> getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.presentation.dashboard.DashboardArrivalItem>> getItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.ActivityLog>> getActivityLogs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getTotal() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getUpdated() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getPending() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedEmployee() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getEmployeeList() {
        return null;
    }
    
    public final void loadToday() {
    }
    
    public final void setSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void setStatusFilter(@org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    public final void setDateFilter(@org.jetbrains.annotations.NotNull()
    java.lang.String date) {
    }
    
    public final void setEmployeeFilter(@org.jetbrains.annotations.NotNull()
    java.lang.String employee) {
    }
    
    private final void applyFilters() {
    }
    
    private final boolean isSameLocalDate(long t1, long t2) {
        return false;
    }
    
    private final boolean isYesterday(long t) {
        return false;
    }
    
    private final boolean isWithinLast7Days(long t) {
        return false;
    }
}