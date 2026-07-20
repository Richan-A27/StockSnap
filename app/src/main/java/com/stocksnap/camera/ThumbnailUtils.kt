package com.stocksnap.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileOutputStream

object ThumbnailUtils {

    private const val TAG = "ImageOptimizer"

    private fun getRotationMatrix(imagePath: String): android.graphics.Matrix {
        val matrix = android.graphics.Matrix()
        try {
            val exif = android.media.ExifInterface(imagePath)
            val orientation = exif.getAttributeInt(android.media.ExifInterface.TAG_ORIENTATION, android.media.ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                android.media.ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                android.media.ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                android.media.ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                android.media.ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1f, 1f)
                android.media.ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return matrix
    }

    fun generateThumbnail(context: Context, imagePath: String): String? {
        return try {
            val file = File(imagePath)
            if (!file.exists()) return null

            val bitmap = BitmapFactory.decodeFile(imagePath) ?: return null
            val matrix = getRotationMatrix(imagePath)
            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

            // Create scaled thumbnail bitmap (150x150)
            val thumbnailBitmap = Bitmap.createScaledBitmap(rotatedBitmap, 150, 150, true)

            val thumbsDir = File(context.getExternalFilesDir(null), "thumbnails")
            if (!thumbsDir.exists()) thumbsDir.mkdirs()

            val thumbFile = File(thumbsDir, "THUMB_${file.name}")
            FileOutputStream(thumbFile).use { out ->
                thumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)
            }

            thumbFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Generates an optimized catalog image for Firebase Storage upload.
     * - Max dimension: 1024px (preserves aspect ratio)
     * - JPEG Quality: 80
     * - Logs original size, optimized size, and compression ratio.
     *
     * @return absolute path to the optimized catalog image, or null on failure.
     */
    fun generateCatalogImage(context: Context, originalPath: String): String? {
        return try {
            val originalFile = File(originalPath)
            if (!originalFile.exists()) return null

            val originalSizeKb = originalFile.length() / 1024

            val bitmap = BitmapFactory.decodeFile(originalPath) ?: return null
            val matrix = getRotationMatrix(originalPath)
            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

            val maxDim = 1024
            val width = rotatedBitmap.width
            val height = rotatedBitmap.height

            val scaledBitmap = if (width > maxDim || height > maxDim) {
                val ratio = if (width > height) {
                    maxDim.toFloat() / width
                } else {
                    maxDim.toFloat() / height
                }
                val newWidth = (width * ratio).toInt()
                val newHeight = (height * ratio).toInt()
                Bitmap.createScaledBitmap(rotatedBitmap, newWidth, newHeight, true)
            } else {
                rotatedBitmap
            }

            val catalogDir = File(context.getExternalFilesDir(null), "catalog_images")
            if (!catalogDir.exists()) catalogDir.mkdirs()

            val catalogFile = File(catalogDir, "CATALOG_${originalFile.name}")
            FileOutputStream(catalogFile).use { out ->
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            }

            val optimizedSizeKb = catalogFile.length() / 1024
            val ratio = if (optimizedSizeKb > 0) {
                ((1.0 - (optimizedSizeKb.toDouble() / originalSizeKb.toDouble())) * 100).toInt()
            } else 0

            Log.i(TAG, "Image optimization complete:")
            Log.i(TAG, "  Original:    ${originalSizeKb} KB")
            Log.i(TAG, "  Optimized:   ${optimizedSizeKb} KB")
            Log.i(TAG, "  Compression: ${ratio}% reduction")

            catalogFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
