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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lcom/stocksnap/presentation/dashboard/DashboardArrivalItem;", "", "arrival", "Lcom/stocksnap/data/model/Arrival;", "product", "Lcom/stocksnap/data/database/Product;", "(Lcom/stocksnap/data/model/Arrival;Lcom/stocksnap/data/database/Product;)V", "getArrival", "()Lcom/stocksnap/data/model/Arrival;", "getProduct", "()Lcom/stocksnap/data/database/Product;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class DashboardArrivalItem {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.model.Arrival arrival = null;
    @org.jetbrains.annotations.Nullable()
    private final com.stocksnap.data.database.Product product = null;
    
    public DashboardArrivalItem(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.Arrival arrival, @org.jetbrains.annotations.Nullable()
    com.stocksnap.data.database.Product product) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.data.model.Arrival getArrival() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.stocksnap.data.database.Product getProduct() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.data.model.Arrival component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.stocksnap.data.database.Product component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.presentation.dashboard.DashboardArrivalItem copy(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.Arrival arrival, @org.jetbrains.annotations.Nullable()
    com.stocksnap.data.database.Product product) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}