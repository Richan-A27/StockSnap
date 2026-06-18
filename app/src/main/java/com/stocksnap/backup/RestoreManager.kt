package com.stocksnap.backup

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.stocksnap.data.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class RestoreManager(
    private val context: Context,
    private val db: AppDatabase,
    private val driveService: DriveService
) {

    suspend fun downloadAndRestore(account: GoogleSignInAccount): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // 1. Download ZIP
            val tempZip = context.cacheDir.resolve("stocksnap_restore.zip")
            tempZip.delete()
            driveService.downloadBackup(account, tempZip)

            // 2. Close DB
            db.close()

            // 3. Unzip
            val parentDir = context.filesDir.parentFile!!
            ZipService.unzipBackup(tempZip, parentDir)

            // 4. Force app restart or reload - usually done by user manually or Restarting Activity
            // For now we assume the DB will be re-opened by Hilt/AppDatabase on next use.
            
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
