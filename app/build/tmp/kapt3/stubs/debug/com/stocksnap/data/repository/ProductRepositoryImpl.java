package com.stocksnap.data.repository;

import android.net.Uri;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.WorkManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.stocksnap.data.database.Product;
import com.stocksnap.data.database.ProductDao;
import com.stocksnap.data.model.ActivityLog;
import com.stocksnap.data.model.Arrival;
import com.stocksnap.data.model.ScanSession;
import com.stocksnap.data.model.User;
import com.stocksnap.data.sync.SyncWorker;
import com.stocksnap.domain.model.ProductStatus;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\u0096@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\u0096@\u00a2\u0006\u0002\u0010\u001cJ\u0010\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020 H\u0002J\u001e\u0010!\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010\u0018J\u001e\u0010\"\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010\u0018J\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$H\u0096@\u00a2\u0006\u0002\u0010&J\u0014\u0010\'\u001a\b\u0012\u0004\u0012\u00020 0$H\u0096@\u00a2\u0006\u0002\u0010&J\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020)0$H\u0096@\u00a2\u0006\u0002\u0010&J\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020+0$H\u0096@\u00a2\u0006\u0002\u0010&J\u0018\u0010,\u001a\u0004\u0018\u00010%2\u0006\u0010-\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010.J\u0018\u0010/\u001a\u0004\u0018\u00010%2\u0006\u0010\u001a\u001a\u00020\u001bH\u0096@\u00a2\u0006\u0002\u0010\u001cJ\u001c\u00100\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u00101\u001a\u00020\u001bH\u0096@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u00102\u001a\u0002032\u0006\u0010\u0015\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010.J$\u00104\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u00105\u001a\u00020\u00162\u0006\u00106\u001a\u000207H\u0096@\u00a2\u0006\u0002\u00108J\u001c\u00109\u001a\b\u0012\u0004\u0012\u00020:0$2\u0006\u0010;\u001a\u000203H\u0096@\u00a2\u0006\u0002\u0010<J\u0018\u0010=\u001a\u0004\u0018\u00010 2\u0006\u00105\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010.J\u0018\u0010>\u001a\u0004\u0018\u00010 2\u0006\u00105\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010.J\u0018\u0010?\u001a\u0004\u0018\u00010 2\u0006\u0010\u001a\u001a\u00020\u001bH\u0096@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u0010@\u001a\u00020\u001b2\u0006\u0010A\u001a\u00020%H\u0096@\u00a2\u0006\u0002\u0010BJ\u0016\u0010C\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 H\u0096@\u00a2\u0006\u0002\u0010DJ&\u0010E\u001a\u00020\u00142\u0006\u00105\u001a\u00020\u00162\u0006\u0010F\u001a\u00020\u00162\u0006\u0010G\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010HJ&\u0010I\u001a\u00020\u00142\u0006\u0010J\u001a\u00020\u00162\u0006\u00105\u001a\u00020\u00162\u0006\u0010F\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010HJ\u001e\u0010K\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010\u0018J\b\u0010L\u001a\u00020\u0014H\u0016J\b\u0010M\u001a\u00020\u0014H\u0016J\b\u0010N\u001a\u00020\u0014H\u0016J\u0016\u0010O\u001a\u00020\u00142\u0006\u0010A\u001a\u00020%H\u0096@\u00a2\u0006\u0002\u0010BJ\u0016\u0010P\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020 H\u0096@\u00a2\u0006\u0002\u0010DJ&\u0010Q\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010R\u001a\u00020SH\u0096@\u00a2\u0006\u0002\u0010TR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006U"}, d2 = {"Lcom/stocksnap/data/repository/ProductRepositoryImpl;", "Lcom/stocksnap/data/repository/ProductRepository;", "dao", "Lcom/stocksnap/data/database/ProductDao;", "firebaseAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "workManager", "Landroidx/work/WorkManager;", "(Lcom/stocksnap/data/database/ProductDao;Lcom/google/firebase/auth/FirebaseAuth;Lcom/google/firebase/firestore/FirebaseFirestore;Lcom/google/firebase/storage/FirebaseStorage;Landroidx/work/WorkManager;)V", "activityListener", "Lcom/google/firebase/firestore/ListenerRegistration;", "arrivalsListener", "productsListener", "scope", "Lkotlinx/coroutines/CoroutineScope;", "approveUser", "", "uid", "", "name", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteArrival", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteProduct", "deleteProductLocalFiles", "product", "Lcom/stocksnap/data/database/Product;", "disableUser", "enableUser", "getAllArrivals", "", "Lcom/stocksnap/data/model/Arrival;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllProducts", "getAllScanSessions", "Lcom/stocksnap/data/model/ScanSession;", "getAllUsers", "Lcom/stocksnap/data/model/User;", "getArrivalByArrivalId", "arrivalId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getArrivalById", "getArrivalsByDay", "day", "getArrivalsCountByUser", "", "getArrivalsForToday", "barcode", "mrp", "", "(Ljava/lang/String;DLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLatestActivityLogs", "Lcom/stocksnap/data/model/ActivityLog;", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProductByBarcode", "getProductByBarcodeFirestore", "getProductById", "insertArrival", "arrival", "(Lcom/stocksnap/data/model/Arrival;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertProduct", "(Lcom/stocksnap/data/database/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logAction", "productName", "action", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logActivityFeed", "actionType", "rejectUser", "startRealtimeSync", "stopRealtimeSync", "triggerSync", "updateArrival", "updateProduct", "updateUserActiveStatus", "isActive", "", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ProductRepositoryImpl implements com.stocksnap.data.repository.ProductRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.database.ProductDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth firebaseAuth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.storage.FirebaseStorage storage = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.work.WorkManager workManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration productsListener;
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration arrivalsListener;
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration activityListener;
    
    @javax.inject.Inject()
    public ProductRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.ProductDao dao, @org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth firebaseAuth, @org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore, @org.jetbrains.annotations.NotNull()
    com.google.firebase.storage.FirebaseStorage storage, @org.jetbrains.annotations.NotNull()
    androidx.work.WorkManager workManager) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertProduct(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateProduct(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteProduct(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getProductById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getProductByBarcode(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getProductByBarcodeFirestore(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAllProducts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.database.Product>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertArrival(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.Arrival arrival, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateArrival(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.Arrival arrival, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteArrival(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getArrivalById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.model.Arrival> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getArrivalByArrivalId(@org.jetbrains.annotations.NotNull()
    java.lang.String arrivalId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.model.Arrival> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAllArrivals(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getArrivalsByDay(long day, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getArrivalsForToday(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, double mrp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.Arrival>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAllScanSessions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.ScanSession>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object logAction(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    java.lang.String productName, @org.jetbrains.annotations.NotNull()
    java.lang.String action, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object logActivityFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String actionType, @org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    java.lang.String productName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getLatestActivityLogs(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.ActivityLog>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    public void startRealtimeSync() {
    }
    
    @java.lang.Override()
    public void stopRealtimeSync() {
    }
    
    @java.lang.Override()
    public void triggerSync() {
    }
    
    private final void deleteProductLocalFiles(com.stocksnap.data.database.Product product) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAllUsers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.model.User>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateUserActiveStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, boolean isActive, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getArrivalsCountByUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object approveUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object rejectUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object disableUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object enableUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}