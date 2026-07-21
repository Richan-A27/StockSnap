package com.stocksnap.presentation.history;

import androidx.lifecycle.ViewModel;
import com.stocksnap.data.database.Product;
import com.stocksnap.data.model.Arrival;
import com.stocksnap.data.repository.ProductRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0002J\b\u0010!\u001a\u00020\u001bH\u0002J\u000e\u0010\"\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\nJ\u000e\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\nJ\u000e\u0010&\u001a\u00020\u001b2\u0006\u0010\'\u001a\u00020\nR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\u000b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R)\u0010\u0011\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\n0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015\u00a8\u0006("}, d2 = {"Lcom/stocksnap/presentation/history/HistoryViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "(Lcom/stocksnap/data/repository/ProductRepository;)V", "_allArrivals", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/stocksnap/presentation/history/HistoryArrivalItem;", "_employeeList", "", "_historyState", "", "employeeList", "Lkotlinx/coroutines/flow/StateFlow;", "getEmployeeList", "()Lkotlinx/coroutines/flow/StateFlow;", "historyState", "getHistoryState", "selectedDate", "getSelectedDate", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "selectedEmployee", "getSelectedEmployee", "selectedStatus", "getSelectedStatus", "applyFilters", "", "isSameDay", "", "time1", "", "time2", "loadHistory", "setDate", "date", "setEmployee", "employee", "setStatus", "status", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HistoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.stocksnap.presentation.history.HistoryArrivalItem>> _allArrivals = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, java.util.List<com.stocksnap.presentation.history.HistoryArrivalItem>>> _historyState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, java.util.List<com.stocksnap.presentation.history.HistoryArrivalItem>>> historyState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _employeeList = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> employeeList = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> selectedStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> selectedEmployee = null;
    
    @javax.inject.Inject()
    public HistoryViewModel(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, java.util.List<com.stocksnap.presentation.history.HistoryArrivalItem>>> getHistoryState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getEmployeeList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getSelectedStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getSelectedEmployee() {
        return null;
    }
    
    private final void loadHistory() {
    }
    
    private final void applyFilters() {
    }
    
    private final boolean isSameDay(long time1, long time2) {
        return false;
    }
    
    public final void setStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    public final void setDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date) {
    }
    
    public final void setEmployee(@org.jetbrains.annotations.NotNull()
    java.lang.String employee) {
    }
}