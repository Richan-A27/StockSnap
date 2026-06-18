package com.stocksnap.di;

import android.content.Context;
import androidx.room.Room;
import com.stocksnap.data.database.AppDatabase;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.data.repository.ProductRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import com.stocksnap.backup.BackupManager;
import com.stocksnap.backup.DriveService;
import com.stocksnap.backup.RestoreManager;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0012\u0010\u000b\u001a\u00020\b2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\f\u001a\u00020\n2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\"\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u0011"}, d2 = {"Lcom/stocksnap/di/AppModule;", "", "()V", "provideBackupManager", "Lcom/stocksnap/backup/BackupManager;", "context", "Landroid/content/Context;", "db", "Lcom/stocksnap/data/database/AppDatabase;", "driveService", "Lcom/stocksnap/backup/DriveService;", "provideDatabase", "provideDriveService", "provideProductRepository", "Lcom/stocksnap/data/repository/ProductRepository;", "provideRestoreManager", "Lcom/stocksnap/backup/RestoreManager;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.stocksnap.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.data.database.AppDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.data.repository.ProductRepository provideProductRepository(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.AppDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.backup.DriveService provideDriveService(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.backup.BackupManager provideBackupManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.AppDatabase db, @org.jetbrains.annotations.NotNull()
    com.stocksnap.backup.DriveService driveService) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.backup.RestoreManager provideRestoreManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.AppDatabase db, @org.jetbrains.annotations.NotNull()
    com.stocksnap.backup.DriveService driveService) {
        return null;
    }
}