package com.stocksnap.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.stocksnap.domain.model.ProductStatus;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b0\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0002B\u00ab\u0001\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0017\u001a\u00020\f\u00a2\u0006\u0002\u0010\u0018J\t\u0010-\u001a\u00020\u0004H\u00c6\u0003J\t\u0010.\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u00101\u001a\u00020\u0004H\u00c6\u0003J\t\u00102\u001a\u00020\u0004H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u00104\u001a\u00020\fH\u00c6\u0003J\t\u00105\u001a\u00020\u0006H\u00c6\u0003J\t\u00106\u001a\u00020\u0006H\u00c6\u0003J\t\u00107\u001a\u00020\u0006H\u00c6\u0003J\t\u00108\u001a\u00020\nH\u00c6\u0003J\t\u00109\u001a\u00020\fH\u00c6\u0003J\t\u0010:\u001a\u00020\u000eH\u00c6\u0003J\t\u0010;\u001a\u00020\u0006H\u00c6\u0003J\t\u0010<\u001a\u00020\u0006H\u00c6\u0003J\u00af\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00062\b\b\u0002\u0010\u0010\u001a\u00020\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u00062\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0014\u001a\u00020\u00042\b\b\u0002\u0010\u0015\u001a\u00020\u00042\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0017\u001a\u00020\fH\u00c6\u0001J\u0013\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010A\u001a\u00020\fH\u00d6\u0001J\u0014\u0010B\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00010CJ\t\u0010D\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\u0014\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0011\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0011\u0010\u0010\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001aR\u0011\u0010\u000f\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001aR\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\u0017\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\"R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001aR\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\"R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0015\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001dR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001aR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001a\u00a8\u0006E"}, d2 = {"Lcom/stocksnap/data/model/Arrival;", "", "()V", "id", "", "arrivalId", "", "barcode", "productName", "mrp", "", "quantityReceived", "", "status", "Lcom/stocksnap/domain/model/ProductStatus;", "createdByUid", "createdByName", "createdByEmail", "updatedByUid", "updatedByName", "createdAt", "updatedAt", "optionalSupplierName", "isPendingSync", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;DILcom/stocksnap/domain/model/ProductStatus;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJLjava/lang/String;I)V", "getArrivalId", "()Ljava/lang/String;", "getBarcode", "getCreatedAt", "()J", "getCreatedByEmail", "getCreatedByName", "getCreatedByUid", "getId", "()I", "getMrp", "()D", "getOptionalSupplierName", "getProductName", "getQuantityReceived", "getStatus", "()Lcom/stocksnap/domain/model/ProductStatus;", "getUpdatedAt", "getUpdatedByName", "getUpdatedByUid", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toFirestoreMap", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "arrivals")
public final class Arrival {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String arrivalId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String barcode = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String productName = null;
    private final double mrp = 0.0;
    private final int quantityReceived = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.domain.model.ProductStatus status = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String createdByUid = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String createdByName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String createdByEmail = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String updatedByUid = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String updatedByName = null;
    private final long createdAt = 0L;
    private final long updatedAt = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String optionalSupplierName = null;
    private final int isPendingSync = 0;
    
    public Arrival(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String arrivalId, @org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    java.lang.String productName, double mrp, int quantityReceived, @org.jetbrains.annotations.NotNull()
    com.stocksnap.domain.model.ProductStatus status, @org.jetbrains.annotations.NotNull()
    java.lang.String createdByUid, @org.jetbrains.annotations.NotNull()
    java.lang.String createdByName, @org.jetbrains.annotations.NotNull()
    java.lang.String createdByEmail, @org.jetbrains.annotations.Nullable()
    java.lang.String updatedByUid, @org.jetbrains.annotations.Nullable()
    java.lang.String updatedByName, long createdAt, long updatedAt, @org.jetbrains.annotations.Nullable()
    java.lang.String optionalSupplierName, int isPendingSync) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getArrivalId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBarcode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProductName() {
        return null;
    }
    
    public final double getMrp() {
        return 0.0;
    }
    
    public final int getQuantityReceived() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.domain.model.ProductStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCreatedByUid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCreatedByName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCreatedByEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUpdatedByUid() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUpdatedByName() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getOptionalSupplierName() {
        return null;
    }
    
    public final int isPendingSync() {
        return 0;
    }
    
    public Arrival() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Object> toFirestoreMap() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final long component14() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component15() {
        return null;
    }
    
    public final int component16() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.domain.model.ProductStatus component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.data.model.Arrival copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String arrivalId, @org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    java.lang.String productName, double mrp, int quantityReceived, @org.jetbrains.annotations.NotNull()
    com.stocksnap.domain.model.ProductStatus status, @org.jetbrains.annotations.NotNull()
    java.lang.String createdByUid, @org.jetbrains.annotations.NotNull()
    java.lang.String createdByName, @org.jetbrains.annotations.NotNull()
    java.lang.String createdByEmail, @org.jetbrains.annotations.Nullable()
    java.lang.String updatedByUid, @org.jetbrains.annotations.Nullable()
    java.lang.String updatedByName, long createdAt, long updatedAt, @org.jetbrains.annotations.Nullable()
    java.lang.String optionalSupplierName, int isPendingSync) {
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