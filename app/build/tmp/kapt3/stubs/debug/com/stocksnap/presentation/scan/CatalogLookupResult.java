package com.stocksnap.presentation.scan;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import com.stocksnap.data.database.Product;
import com.stocksnap.data.model.Arrival;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.ocr.MLKitProcessor;
import com.stocksnap.ocr.QrCodeDetectedException;
import com.stocksnap.camera.ThumbnailUtils;
import com.stocksnap.domain.model.ProductStatus;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/stocksnap/presentation/scan/CatalogLookupResult;", "", "()V", "Found", "Idle", "Loading", "NotFound", "Lcom/stocksnap/presentation/scan/CatalogLookupResult$Found;", "Lcom/stocksnap/presentation/scan/CatalogLookupResult$Idle;", "Lcom/stocksnap/presentation/scan/CatalogLookupResult$Loading;", "Lcom/stocksnap/presentation/scan/CatalogLookupResult$NotFound;", "app_debug"})
public abstract class CatalogLookupResult {
    
    private CatalogLookupResult() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/stocksnap/presentation/scan/CatalogLookupResult$Found;", "Lcom/stocksnap/presentation/scan/CatalogLookupResult;", "product", "Lcom/stocksnap/data/database/Product;", "(Lcom/stocksnap/data/database/Product;)V", "getProduct", "()Lcom/stocksnap/data/database/Product;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Found extends com.stocksnap.presentation.scan.CatalogLookupResult {
        @org.jetbrains.annotations.NotNull()
        private final com.stocksnap.data.database.Product product = null;
        
        public Found(@org.jetbrains.annotations.NotNull()
        com.stocksnap.data.database.Product product) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.stocksnap.data.database.Product getProduct() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.stocksnap.data.database.Product component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.stocksnap.presentation.scan.CatalogLookupResult.Found copy(@org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/stocksnap/presentation/scan/CatalogLookupResult$Idle;", "Lcom/stocksnap/presentation/scan/CatalogLookupResult;", "()V", "app_debug"})
    public static final class Idle extends com.stocksnap.presentation.scan.CatalogLookupResult {
        @org.jetbrains.annotations.NotNull()
        public static final com.stocksnap.presentation.scan.CatalogLookupResult.Idle INSTANCE = null;
        
        private Idle() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/stocksnap/presentation/scan/CatalogLookupResult$Loading;", "Lcom/stocksnap/presentation/scan/CatalogLookupResult;", "()V", "app_debug"})
    public static final class Loading extends com.stocksnap.presentation.scan.CatalogLookupResult {
        @org.jetbrains.annotations.NotNull()
        public static final com.stocksnap.presentation.scan.CatalogLookupResult.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/stocksnap/presentation/scan/CatalogLookupResult$NotFound;", "Lcom/stocksnap/presentation/scan/CatalogLookupResult;", "()V", "app_debug"})
    public static final class NotFound extends com.stocksnap.presentation.scan.CatalogLookupResult {
        @org.jetbrains.annotations.NotNull()
        public static final com.stocksnap.presentation.scan.CatalogLookupResult.NotFound INSTANCE = null;
        
        private NotFound() {
        }
    }
}