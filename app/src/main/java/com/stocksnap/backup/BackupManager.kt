package com.stocksnap.backup

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.stocksnap.data.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class BackupManager(
    private val context: Context,
    private val db: AppDatabase,
    private val driveService: DriveService
) {

    suspend fun createAndUploadBackup(account: GoogleSignInAccount): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // 1. Checkpoint DB to ensure all data is in the main file
            db.openHelper.writableDatabase.execSQL("PRAGMA wal_checkpoint(FULL)")

            val backupDir = context.cacheDir.resolve("stocksnap_backup_tmp")
            backupDir.deleteRecursively()
            backupDir.mkdirs()

            // 2. Locate DB files
            val dbFile = context.getDatabasePath("stocksnap-db")
            val imagesDir = context.getExternalFilesDir(null) ?: throw Exception("Internal storage unavailable")

            // 3. Prepare ZIP
            val tempZip = context.cacheDir.resolve("stocksnap_backup.zip")
            tempZip.delete()

            val filesToZip = mutableListOf<File>()
            if (dbFile.exists()) filesToZip.add(dbFile)
            if (imagesDir.exists()) filesToZip.add(imagesDir)

            ZipService.createBackupZip(tempZip, filesToZip, context.filesDir.parentFile!!)

            // 4. Upload
            driveService.uploadBackup(account, tempZip)
            
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
