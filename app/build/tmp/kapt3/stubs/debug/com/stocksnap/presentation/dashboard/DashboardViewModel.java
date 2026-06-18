package com.stocksnap.presentation.dashboard;

import androidx.lifecycle.ViewModel;
import com.stocksnap.data.database.Product;
import com.stocksnap.data.repository.ProductRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\bR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010\u00a8\u0006\u001b"}, d2 = {"Lcom/stocksnap/presentation/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "(Lcom/stocksnap/data/repository/ProductRepository;)V", "_items", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/stocksnap/data/database/Product;", "_pending", "", "_total", "_updated", "items", "Lkotlinx/coroutines/flow/StateFlow;", "getItems", "()Lkotlinx/coroutines/flow/StateFlow;", "pending", "getPending", "total", "getTotal", "updated", "getUpdated", "loadToday", "", "toggleStatus", "product", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.stocksnap.data.database.Product>> _items = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.database.Product>> items = null;
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
    
    @javax.inject.Inject()
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.database.Product>> getItems() {
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
    
    public final void loadToday() {
    }
    
    public final void toggleStatus(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product) {
    }
}