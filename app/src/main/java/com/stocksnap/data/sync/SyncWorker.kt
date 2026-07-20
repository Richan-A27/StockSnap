package com.stocksnap.data.sync

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.stocksnap.camera.ThumbnailUtils
import com.stocksnap.data.database.AppDatabase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await
import java.io.File

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface SyncWorkerEntryPoint {
        fun appDatabase(): AppDatabase
        fun firestore(): FirebaseFirestore
        fun storage(): FirebaseStorage
    }

    companion object {
        private const val TAG = "SyncWorker"
    }

    override suspend fun doWork(): Result {
        val appContext = applicationContext
        val entryPoint = EntryPointAccessors.fromApplication(
            appContext,
            SyncWorkerEntryPoint::class.java
        )
        val db = entryPoint.appDatabase()
        val dao = db.productDao()
        val firestore = entryPoint.firestore()
        val storage = entryPoint.storage()

        return try {
            // 1. Sync Products (Catalog) — upload OPTIMIZED image only
            val unsyncedProducts = dao.getUnsyncedProducts()
            for (product in unsyncedProducts) {
                var currentUrl = product.frontImageUrl

                if (currentUrl.isNullOrEmpty() && product.frontImagePath.isNotEmpty()) {
                    // Generate optimized catalog image from original
                    val catalogPath = product.catalogImagePath
                        ?: ThumbnailUtils.generateCatalogImage(appContext, product.frontImagePath)

                    val fileToUpload = if (catalogPath != null) File(catalogPath) else null

                    if (fileToUpload != null && fileToUpload.exists()) {
                        try {
                            val originalSize = File(product.frontImagePath).length() / 1024
                            val optimizedSize = fileToUpload.length() / 1024

                            Log.i(TAG, "Uploading optimized image for ${product.barcode}:")
                            Log.i(TAG, "  Original:  ${originalSize} KB")
                            Log.i(TAG, "  Uploading: ${optimizedSize} KB")

                            val ref = storage.reference.child("products/${product.barcode}/front.jpg")
                            ref.putFile(Uri.fromFile(fileToUpload)).await()
                            currentUrl = ref.downloadUrl.await().toString()

                            // Update catalogImagePath in Room if generated during sync
                            if (product.catalogImagePath == null && catalogPath != null) {
                                dao.update(product.copy(catalogImagePath = catalogPath))
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                val updatedProduct = if (currentUrl != product.frontImageUrl) {
                    product.copy(frontImageUrl = currentUrl)
                } else {
                    product
                }

                // Upload to Firestore
                firestore.collection("products")
                    .document(updatedProduct.barcode)
                    .set(updatedProduct.toFirestoreMap())
                    .await()

                if (!currentUrl.isNullOrEmpty()) {
                    dao.markProductSyncedWithUrl(updatedProduct.id, currentUrl)
                } else {
                    dao.markProductSynced(updatedProduct.id)
                }
            }

            // 2. Sync Arrivals
            val unsyncedArrivals = dao.getUnsyncedArrivals()
            for (arrival in unsyncedArrivals) {
                firestore.collection("arrivals")
                    .document(arrival.arrivalId)
                    .set(arrival.toFirestoreMap())
                    .await()
                dao.markArrivalSynced(arrival.id)
            }

            // 3. Sync Scan Sessions (local operational history)
            val unsyncedSessions = dao.getUnsyncedScanSessions()
            for (session in unsyncedSessions) {
                firestore.collection("scanSessions")
                    .add(session.toMap())
                    .await()
                dao.markScanSessionSynced(session.id)
            }

            // 4. Sync Activity Logs (shared collaborative feed)
            val unsyncedLogs = dao.getUnsyncedActivityLogs()
            for (log in unsyncedLogs) {
                firestore.collection("activity_logs")
                    .document(log.activityId)
                    .set(log.toFirestoreMap())
                    .await()
                dao.markActivityLogSynced(log.id)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
