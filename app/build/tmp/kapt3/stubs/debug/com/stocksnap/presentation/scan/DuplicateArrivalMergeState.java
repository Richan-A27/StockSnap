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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/stocksnap/presentation/scan/DuplicateArrivalMergeState;", "", "()V", "Idle", "PendingMerge", "Lcom/stocksnap/presentation/scan/DuplicateArrivalMergeState$Idle;", "Lcom/stocksnap/presentation/scan/DuplicateArrivalMergeState$PendingMerge;", "app_debug"})
public abstract class DuplicateArrivalMergeState {
    
    private DuplicateArrivalMergeState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/stocksnap/presentation/scan/DuplicateArrivalMergeState$Idle;", "Lcom/stocksnap/presentation/scan/DuplicateArrivalMergeState;", "()V", "app_debug"})
    public static final class Idle extends com.stocksnap.presentation.scan.DuplicateArrivalMergeState {
        @org.jetbrains.annotations.NotNull()
        public static final com.stocksnap.presentation.scan.DuplicateArrivalMergeState.Idle INSTANCE = null;
        
        private Idle() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\fH\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u000eH\u00c6\u0003JO\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eH\u00c6\u0001J\u0013\u0010\"\u001a\u00020\f2\b\u0010#\u001a\u0004\u0018\u00010$H\u00d6\u0003J\t\u0010%\u001a\u00020\u0005H\u00d6\u0001J\t\u0010&\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0012R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\'"}, d2 = {"Lcom/stocksnap/presentation/scan/DuplicateArrivalMergeState$PendingMerge;", "Lcom/stocksnap/presentation/scan/DuplicateArrivalMergeState;", "existingArrival", "Lcom/stocksnap/data/model/Arrival;", "newQuantity", "", "newSupplier", "", "onSaved", "Lkotlin/Function0;", "", "isNewProduct", "", "newProduct", "Lcom/stocksnap/data/database/Product;", "(Lcom/stocksnap/data/model/Arrival;ILjava/lang/String;Lkotlin/jvm/functions/Function0;ZLcom/stocksnap/data/database/Product;)V", "getExistingArrival", "()Lcom/stocksnap/data/model/Arrival;", "()Z", "getNewProduct", "()Lcom/stocksnap/data/database/Product;", "getNewQuantity", "()I", "getNewSupplier", "()Ljava/lang/String;", "getOnSaved", "()Lkotlin/jvm/functions/Function0;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "", "hashCode", "toString", "app_debug"})
    public static final class PendingMerge extends com.stocksnap.presentation.scan.DuplicateArrivalMergeState {
        @org.jetbrains.annotations.NotNull()
        private final com.stocksnap.data.model.Arrival existingArrival = null;
        private final int newQuantity = 0;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String newSupplier = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.jvm.functions.Function0<kotlin.Unit> onSaved = null;
        private final boolean isNewProduct = false;
        @org.jetbrains.annotations.Nullable()
        private final com.stocksnap.data.database.Product newProduct = null;
        
        public PendingMerge(@org.jetbrains.annotations.NotNull()
        com.stocksnap.data.model.Arrival existingArrival, int newQuantity, @org.jetbrains.annotations.Nullable()
        java.lang.String newSupplier, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function0<kotlin.Unit> onSaved, boolean isNewProduct, @org.jetbrains.annotations.Nullable()
        com.stocksnap.data.database.Product newProduct) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.stocksnap.data.model.Arrival getExistingArrival() {
            return null;
        }
        
        public final int getNewQuantity() {
            return 0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getNewSupplier() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlin.jvm.functions.Function0<kotlin.Unit> getOnSaved() {
            return null;
        }
        
        public final boolean isNewProduct() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.stocksnap.data.database.Product getNewProduct() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.stocksnap.data.model.Arrival component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlin.jvm.functions.Function0<kotlin.Unit> component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.stocksnap.data.database.Product component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.stocksnap.presentation.scan.DuplicateArrivalMergeState.PendingMerge copy(@org.jetbrains.annotations.NotNull()
        com.stocksnap.data.model.Arrival existingArrival, int newQuantity, @org.jetbrains.annotations.Nullable()
        java.lang.String newSupplier, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function0<kotlin.Unit> onSaved, boolean isNewProduct, @org.jetbrains.annotations.Nullable()
        com.stocksnap.data.database.Product newProduct) {
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
}