package com.stocksnap.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.stocksnap.data.model.Arrival
import com.stocksnap.data.model.ScanSession
import com.stocksnap.data.model.ActivityLog

@Database(
    entities = [Product::class, Arrival::class, ScanSession::class, ActivityLog::class],
    version = 6
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE products ADD COLUMN thumbnailPath TEXT")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE products ADD COLUMN weight TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE products ADD COLUMN frontImageUrl TEXT")
                db.execSQL("ALTER TABLE products ADD COLUMN isPendingSync INTEGER NOT NULL DEFAULT 0")

                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `arrivals` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `arrivalId` TEXT NOT NULL,
                        `barcode` TEXT NOT NULL,
                        `productName` TEXT NOT NULL,
                        `mrp` REAL NOT NULL,
                        `quantityReceived` INTEGER NOT NULL,
                        `status` TEXT NOT NULL,
                        `createdByUid` TEXT NOT NULL,
                        `createdByName` TEXT NOT NULL,
                        `createdByEmail` TEXT NOT NULL,
                        `updatedByUid` TEXT,
                        `updatedByName` TEXT,
                        `createdAt` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL,
                        `optionalSupplierName` TEXT,
                        `isPendingSync` INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())

                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `scan_sessions` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `employeeUid` TEXT NOT NULL,
                        `employeeName` TEXT NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        `barcode` TEXT NOT NULL,
                        `productName` TEXT NOT NULL,
                        `action` TEXT NOT NULL,
                        `isPendingSync` INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Add catalogImagePath to products for optimized image storage
                db.execSQL("ALTER TABLE products ADD COLUMN catalogImagePath TEXT")

                // Create activity_logs table (coexists with scan_sessions)
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `activity_logs` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `activityId` TEXT NOT NULL,
                        `performedByUid` TEXT NOT NULL,
                        `performedByName` TEXT NOT NULL,
                        `performedByEmail` TEXT NOT NULL,
                        `actionType` TEXT NOT NULL,
                        `productName` TEXT NOT NULL,
                        `barcode` TEXT NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        `isPendingSync` INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE products ADD COLUMN code TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}
