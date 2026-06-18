package com.stocksnap.di

import android.content.Context
import androidx.room.Room
import com.stocksnap.data.database.AppDatabase
import com.stocksnap.data.repository.ProductRepository
import com.stocksnap.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import com.stocksnap.backup.BackupManager
import com.stocksnap.backup.DriveService
import com.stocksnap.backup.RestoreManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "stocksnap-db")
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductRepository(db: AppDatabase): ProductRepository {
        return ProductRepositoryImpl(db.productDao())
    }

    @Provides
    @Singleton
    fun provideDriveService(@ApplicationContext context: Context): DriveService {
        return DriveService(context)
    }

    @Provides
    @Singleton
    fun provideBackupManager(
        @ApplicationContext context: Context,
        db: AppDatabase,
        driveService: DriveService
    ): BackupManager {
        return BackupManager(context, db, driveService)
    }

    @Provides
    @Singleton
    fun provideRestoreManager(
        @ApplicationContext context: Context,
        db: AppDatabase,
        driveService: DriveService
    ): RestoreManager {
        return RestoreManager(context, db, driveService)
    }
}
