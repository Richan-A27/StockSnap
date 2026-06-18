package com.stocksnap.backup

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileOutputStream

class DriveService(private val context: Context) {

    private val scopes = listOf(DriveScopes.DRIVE_APPDATA)

    fun getGoogleSignInClient() = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestScopes(com.google.android.gms.common.api.Scope(DriveScopes.DRIVE_APPDATA))
            .build()
    )

    private fun getDriveService(account: GoogleSignInAccount): Drive {
        val credential = GoogleAccountCredential.usingOAuth2(context, scopes)
        credential.selectedAccount = account.account
        return Drive.Builder(
            NetHttpTransport(),
            GsonFactory(),
            credential
        ).setApplicationName("StockSnap").build()
    }

    suspend fun uploadBackup(account: GoogleSignInAccount, zipFile: java.io.File) = withContext(Dispatchers.IO) {
        val service = getDriveService(account)
        
        // 1. Delete old backups in appDataFolder
        val existing = service.files().list()
            .setSpaces("appDataFolder")
            .setFields("files(id, name)")
            .execute()
        
        existing.files.filter { it.name == "stocksnap_backup.zip" }.forEach {
            service.files().delete(it.id).execute()
        }

        // 2. Upload new
        val fileMetadata = File().apply {
            name = "stocksnap_backup.zip"
            parents = listOf("appDataFolder")
        }
        val mediaContent = FileContent("application/zip", zipFile)
        service.files().create(fileMetadata, mediaContent).execute()
    }

    suspend fun downloadBackup(account: GoogleSignInAccount, targetZip: java.io.File) = withContext(Dispatchers.IO) {
        val service = getDriveService(account)
        
        val files = service.files().list()
            .setSpaces("appDataFolder")
            .setQ("name = 'stocksnap_backup.zip'")
            .setFields("files(id, name)")
            .execute()
            .files

        if (files.isEmpty()) throw Exception("No backup found in Google Drive")

        val fileId = files[0].id
        service.files().get(fileId).executeMediaAndDownloadTo(FileOutputStream(targetZip))
    }
}
