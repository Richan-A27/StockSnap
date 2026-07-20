package com.stocksnap.utils

import android.content.Context
import android.util.Log
import com.stocksnap.camera.ThumbnailUtils
import com.stocksnap.data.database.ProductDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Migration utility for existing products.
 * Regenerates optimized catalog images from originals
 * and marks products as pending sync so SyncWorker
 * re-uploads the compressed versions.
 */
@Singleton
class MigrationUtility @Inject constructor(
    private val dao: ProductDao
) {
    companion object {
        private const val TAG = "MigrationUtility"
    }

    /**
     * Scans all products that have a local frontImagePath but no catalogImagePath.
     * For each, generates an optimized catalog image and marks the product for re-sync.
     *
     * @return number of products migrated
     */
    suspend fun migrateExistingImages(context: Context): Int {
        val allProducts = dao.getAllProducts()
        var migratedCount = 0

        for (product in allProducts) {
            // Skip if no local image or already has a catalog image
            if (product.frontImagePath.isEmpty()) continue
            if (!product.catalogImagePath.isNullOrEmpty()) continue

            val catalogPath = ThumbnailUtils.generateCatalogImage(context, product.frontImagePath)
            if (catalogPath != null) {
                // Update product with catalog path and mark for re-sync
                val updated = product.copy(
                    catalogImagePath = catalogPath,
                    frontImageUrl = null, // Force re-upload of optimized version
                    isPendingSync = 1
                )
                dao.update(updated)
                migratedCount++
                Log.i(TAG, "Migrated image for product: ${product.barcode} (${product.name})")
            } else {
                Log.w(TAG, "Failed to generate catalog image for: ${product.barcode}")
            }
        }

        Log.i(TAG, "Migration complete. $migratedCount products migrated.")
        return migratedCount
    }
}
