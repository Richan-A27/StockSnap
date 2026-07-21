package com.stocksnap.data.repository;

import com.stocksnap.data.database.Product;
import com.stocksnap.data.model.ActivityLog;
import com.stocksnap.data.model.Arrival;
import com.stocksnap.data.model.ScanSession;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00a6@\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0010H\u00a6@\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0010H\u00a6@\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0010H\u00a6@\u00a2\u0006\u0002\u0010\u0012J\u0018\u0010\u0019\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u001a\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u001bJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u00112\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u001e\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\u001f\u001a\u00020 2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u001bJ$\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$H\u00a6@\u00a2\u0006\u0002\u0010%J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00102\b\b\u0002\u0010(\u001a\u00020 H\u00a6@\u00a2\u0006\u0002\u0010)J\u0018\u0010*\u001a\u0004\u0018\u00010\u00142\u0006\u0010\"\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u001bJ\u0018\u0010+\u001a\u0004\u0018\u00010\u00142\u0006\u0010\"\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u001bJ\u0018\u0010,\u001a\u0004\u0018\u00010\u00142\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010-\u001a\u00020\n2\u0006\u0010.\u001a\u00020\u0011H\u00a6@\u00a2\u0006\u0002\u0010/J\u0016\u00100\u001a\u00020\n2\u0006\u00101\u001a\u00020\u0014H\u00a6@\u00a2\u0006\u0002\u00102J&\u00103\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\u00052\u0006\u00104\u001a\u00020\u00052\u0006\u00105\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u00106J&\u00107\u001a\u00020\u00032\u0006\u00108\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u00052\u0006\u00104\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u00106J\u001e\u00109\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\b\u0010:\u001a\u00020\u0003H&J\b\u0010;\u001a\u00020\u0003H&J\b\u0010<\u001a\u00020\u0003H&J\u0016\u0010=\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u0011H\u00a6@\u00a2\u0006\u0002\u0010/J\u0016\u0010>\u001a\u00020\u00032\u0006\u00101\u001a\u00020\u0014H\u00a6@\u00a2\u0006\u0002\u00102J&\u0010?\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010@\u001a\u00020AH\u00a6@\u00a2\u0006\u0002\u0010B\u00a8\u0006C"}, d2 = {"Lcom/stocksnap/data/repository/ProductRepository;", "", "approveUser", "", "uid", "", "name", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteArrival", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteProduct", "disableUser", "enableUser", "getAllArrivals", "", "Lcom/stocksnap/data/model/Arrival;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllProducts", "Lcom/stocksnap/data/database/Product;", "getAllScanSessions", "Lcom/stocksnap/data/model/ScanSession;", "getAllUsers", "Lcom/stocksnap/data/model/User;", "getArrivalByArrivalId", "arrivalId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getArrivalById", "getArrivalsByDay", "day", "getArrivalsCountByUser", "", "getArrivalsForToday", "barcode", "mrp", "", "(Ljava/lang/String;DLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLatestActivityLogs", "Lcom/stocksnap/data/model/ActivityLog;", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProductByBarcode", "getProductByBarcodeFirestore", "getProductById", "insertArrival", "arrival", "(Lcom/stocksnap/data/model/Arrival;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertProduct", "product", "(Lcom/stocksnap/data/database/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logAction", "productName", "action", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logActivityFeed", "actionType", "rejectUser", "startRealtimeSync", "stopRealtimeSync", "triggerSync", "updateArrival", "updateProduct", "updateUserActiveStatus", "isActive", "", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ProductRepository {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertProduct(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateProduct(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteProduct(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProductById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProductByBarcode(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProductByBarcodeFirestore(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllProducts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.database.Product>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertArrival(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.Arrival arrival, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateArrival(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.Arrival arrival, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteArrival(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArrivalById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.model.Arrival> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArrivalByArrivalId(@org.jetbrains.annotations.NotNull()
    java.lang.String arrivalId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.model.Arrival> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllArrivals(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArrivalsByDay(long day, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArrivalsForToday(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, double mrp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllScanSessions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.ScanSession>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object logAction(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    java.lang.String productName, @org.jetbrains.annotations.NotNull()
    java.lang.String action, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object logActivityFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String actionType, @org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    java.lang.String productName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLatestActivityLogs(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.ActivityLog>> $completion);
    
    public abstract void startRealtimeSync();
    
    public abstract void stopRealtimeSync();
    
    public abstract void triggerSync();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllUsers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.User>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateUserActiveStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, boolean isActive, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArrivalsCountByUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object approveUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object rejectUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object disableUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object enableUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}