package com.stocksnap.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0014\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u001a"}, d2 = {"Lcom/stocksnap/data/database/ProductDao;", "", "countByStatus", "", "status", "Lcom/stocksnap/domain/model/ProductStatus;", "(Lcom/stocksnap/domain/model/ProductStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findByBarcode", "Lcom/stocksnap/data/database/Product;", "barcode", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByDay", "day", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getById", "id", "insert", "product", "(Lcom/stocksnap/data/database/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "app_debug"})
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
    
    @androidx.room.Query(value = "SELECT * FROM products WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM products ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.database.Product>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM products WHERE DATE(createdAt / 1000, \'unixepoch\') = DATE(:day / 1000, \'unixepoch\') ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByDay(long day, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stocksnap.data.database.Product>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM products WHERE status = :status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countByStatus(@org.jetbrains.annotations.NotNull()
    com.stocksnap.domain.model.ProductStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM products WHERE barcode = :barcode LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findByBarcode(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion);
}