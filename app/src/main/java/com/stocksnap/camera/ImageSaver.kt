package com.stocksnap.camera

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

object ImageSaver {
    fun createImageFile(context: Context): File {
        val imagesDir = File(context.getExternalFilesDir(null), "images")
        if (!imagesDir.exists()) imagesDir.mkdirs()
        val time = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis())
        return File(imagesDir, "IMG_$time.jpg")
    }
}
