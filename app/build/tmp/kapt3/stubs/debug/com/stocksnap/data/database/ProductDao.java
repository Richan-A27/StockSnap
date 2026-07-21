package com.stocksnap.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.stocksnap.data.model.Arrival;
import com.stocksnap.data.model.ScanSession;
import com.stocksnap.data.model.ActivityLog;
import com.stocksnap.domain.model.ProductStatus;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001c\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u00162\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u00162\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u001e\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ4\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010$J\u0018\u0010%\u001a\u0004\u0018\u00010\u00122\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00152\b\b\u0002\u0010(\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010)J\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020\'0\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010.\u001a\u00020\u000e2\u0006\u0010/\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u00100J\u0016\u00101\u001a\u00020\u000e2\u0006\u00102\u001a\u00020\'H\u00a7@\u00a2\u0006\u0002\u00103J\u0016\u00104\u001a\u00020\u000e2\u0006\u00105\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u00106J\u0016\u00107\u001a\u00020\u000e2\u0006\u00108\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u00109J\u0016\u0010:\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010;\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010<\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010=\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010>\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010?J\u0016\u0010@\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010A\u001a\u00020\b2\u0006\u0010/\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u00100J\u0016\u0010B\u001a\u00020\b2\u0006\u00105\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u00106\u00a8\u0006C"}, d2 = {"Lcom/stocksnap/data/database/ProductDao;", "", "countArrivalsByStatus", "", "status", "Lcom/stocksnap/domain/model/ProductStatus;", "(Lcom/stocksnap/domain/model/ProductStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteArrivalByArrivalId", "", "arrivalId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteArrivalById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteProductById", "findByBarcode", "Lcom/stocksnap/data/database/Product;", "barcode", "getAllArrivals", "", "Lcom/stocksnap/data/model/Arrival;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllProducts", "getAllScanSessions", "Lcom/stocksnap/data/model/ScanSession;", "getArrivalByArrivalId", "getArrivalById", "getArrivalsByDay", "day", "getArrivalsForToday", "mrp", "", "startOfDay", "endOfDay", "(Ljava/lang/String;DJJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getById", "getLatestActivityLogs", "Lcom/stocksnap/data/model/ActivityLog;", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUnsyncedActivityLogs", "getUnsyncedArrivals", "getUnsyncedProducts", "getUnsyncedScanSessions", "insert", "product", "(Lcom/stocksnap/data/database/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertActivityLog", "log", "(Lcom/stocksnap/data/model/ActivityLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertArrival", "arrival", "(Lcom/stocksnap/data/model/Arrival;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertScanSession", "session", "(Lcom/stocksnap/data/model/ScanSession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markActivityLogSynced", "markArrivalSynced", "markProductSynced", "markProductSyncedWithUrl", "imageUrl", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markScanSessionSynced", "update", "updateArrival", "app_debug"})
@androidx.room.Dao()
public abstract interface ProductDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM products WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteProductById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM products WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM products WHERE barcode = :barcode LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findByBarcode(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM products ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllProducts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.database.Product>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM products WHERE isPendingSync = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedProducts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.database.Product>> $completion);
    
    @androidx.room.Query(value = "UPDATE products SET isPendingSync = 0 WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markProductSynced(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE products SET isPendingSync = 0, frontImageUrl = :imageUrl WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markProductSyncedWithUrl(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String imageUrl, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertArrival(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.Arrival arrival, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateArrival(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.Arrival arrival, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM arrivals WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteArrivalById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM arrivals WHERE arrivalId = :arrivalId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteArrivalByArrivalId(@org.jetbrains.annotations.NotNull()
    java.lang.String arrivalId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM arrivals WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArrivalById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.model.Arrival> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM arrivals WHERE arrivalId = :arrivalId LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArrivalByArrivalId(@org.jetbrains.annotations.NotNull()
    java.lang.String arrivalId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.model.Arrival> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM arrivals ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllArrivals(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM arrivals WHERE DATE(createdAt / 1000, \'unixepoch\') = DATE(:day / 1000, \'unixepoch\') ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArrivalsByDay(long day, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM arrivals WHERE barcode = :barcode AND mrp = :mrp AND createdAt >= :startOfDay AND createdAt <= :endOfDay ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArrivalsForToday(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, double mrp, long startOfDay, long endOfDay, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM arrivals WHERE status = :status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countArrivalsByStatus(@org.jetbrains.annotations.NotNull()
    com.stocksnap.domain.model.ProductStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM arrivals WHERE isPendingSync = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedArrivals(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion);
    
    @androidx.room.Query(value = "UPDATE arrivals SET isPendingSync = 0 WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markArrivalSynced(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertScanSession(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.ScanSession session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM scan_sessions WHERE isPendingSync = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedScanSessions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.ScanSession>> $completion);
    
    @androidx.room.Query(value = "UPDATE scan_sessions SET isPendingSync = 0 WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markScanSessionSynced(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM scan_sessions ORDER BY timestamp DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllScanSessions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.ScanSession>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertActivityLog(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.ActivityLog log, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM activity_logs WHERE isPendingSync = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedActivityLogs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.ActivityLog>> $completion);
    
    @androidx.room.Query(value = "UPDATE activity_logs SET isPendingSync = 0 WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markActivityLogSynced(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT :limit")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLatestActivityLogs(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.ActivityLog>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}