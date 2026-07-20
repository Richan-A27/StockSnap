package com.stocksnap.camera

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun BarcodeScannerCamera(
    modifier: Modifier = Modifier,
    onBarcodeDetected: (String) -> Unit,
    onQrCodeRejected: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = context as? LifecycleOwner ?: return
    val previewView = remember { PreviewView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    } }
    
    var detectedBoundingBox by remember { mutableStateOf<android.graphics.Rect?>(null) }
    var imageWidth by remember { mutableStateOf(0) }
    var imageHeight by remember { mutableStateOf(0) }
    var isProcessing by remember { mutableStateOf(false) }

    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    
    val options = remember {
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_UPC_E,
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_DATA_MATRIX,
                Barcode.FORMAT_AZTEC,
                Barcode.FORMAT_PDF417
            )
            .build()
    }
    val scanner = remember { BarcodeScanning.getClient(options) }

    var scanStatus by remember { mutableStateOf("Scanning...") }
    var consecutiveCount by remember { mutableStateOf(0) }
    var lastBarcodeValue by remember { mutableStateOf("") }
    var lastScanTime by remember { mutableStateOf(0L) }

    DisposableEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { analysis ->
                    analysis.setAnalyzer(cameraExecutor) { imageProxy ->
                        if (isProcessing || System.currentTimeMillis() - lastScanTime < 2000L) {
                            imageProxy.close()
                            return@setAnalyzer
                        }

                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                            
                            // Update dimensions for bounding box scaling
                            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                            if (rotationDegrees == 90 || rotationDegrees == 270) {
                                imageWidth = image.height
                                imageHeight = image.width
                            } else {
                                imageWidth = image.width
                                imageHeight = image.height
                            }

                            scanner.process(image)
                                .addOnSuccessListener { barcodes ->
                                    if (barcodes.isNotEmpty()) {
                                        val allowedFormats = listOf(
                                            Barcode.FORMAT_EAN_13,
                                            Barcode.FORMAT_EAN_8,
                                            Barcode.FORMAT_UPC_A,
                                            Barcode.FORMAT_UPC_E,
                                            Barcode.FORMAT_CODE_128
                                        )
                                        
                                        // Largest Barcode Wins
                                        val allowedBarcode = barcodes
                                            .filter { it.format in allowedFormats && it.boundingBox != null }
                                            .maxByOrNull { it.boundingBox!!.width() * it.boundingBox!!.height() }
                                            
                                        if (allowedBarcode != null) {
                                            val currentVal = allowedBarcode.rawValue ?: ""
                                            if (currentVal == lastBarcodeValue) {
                                                consecutiveCount++
                                            } else {
                                                lastBarcodeValue = currentVal
                                                consecutiveCount = 1
                                            }
                                            
                                            detectedBoundingBox = allowedBarcode.boundingBox

                                            if (consecutiveCount >= 3) {
                                                scanStatus = "✓ Barcode Confirmed"
                                                isProcessing = true
                                                lastScanTime = System.currentTimeMillis()
                                                
                                                // Vibrate
                                                val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                                    val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                                                    vibratorManager.defaultVibrator
                                                } else {
                                                    @Suppress("DEPRECATION")
                                                    context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                                                }
                                                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                                                
                                                // Beep
                                                val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                                                toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 150)
                                                
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(400)
                                                    onBarcodeDetected(currentVal)
                                                    // Reset after callback just in case it doesn't navigate away
                                                    isProcessing = false
                                                    scanStatus = "Scanning..."
                                                    consecutiveCount = 0
                                                    lastBarcodeValue = ""
                                                }
                                            } else {
                                                scanStatus = "Verifying..."
                                            }
                                        } else {
                                            scanStatus = "Scanning..."
                                            consecutiveCount = 0
                                            
                                            val ignoredFormats = listOf(
                                                Barcode.FORMAT_QR_CODE,
                                                Barcode.FORMAT_DATA_MATRIX,
                                                Barcode.FORMAT_AZTEC,
                                                Barcode.FORMAT_PDF417
                                            )
                                            val ignoredBarcode = barcodes.firstOrNull { it.format in ignoredFormats }
                                            if (ignoredBarcode != null) {
                                                onQrCodeRejected("Please scan the product barcode.")
                                            }
                                        }
                                    } else {
                                        scanStatus = "Scanning..."
                                        consecutiveCount = 0
                                        detectedBoundingBox = null
                                    }
                                }
                                .addOnCompleteListener {
                                    imageProxy.close()
                                }
                        } else {
                            imageProxy.close()
                        }
                    }
                }
                
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e("BarcodeScannerCamera", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
        
        onDispose {
            cameraExecutor.shutdown()
            scanner.close()
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
        
        // Draw green bounding box if detected
        detectedBoundingBox?.let { box ->
            if (imageWidth > 0 && imageHeight > 0) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val scaleX = size.width / imageWidth
                    val scaleY = size.height / imageHeight
                    
                    val left = box.left * scaleX
                    val top = box.top * scaleY
                    val right = box.right * scaleX
                    val bottom = box.bottom * scaleY
                    
                    drawRect(
                        color = Color(0xFF4CAF50),
                        topLeft = Offset(left, top),
                        size = Size(right - left, bottom - top),
                        style = Stroke(width = 8f)
                    )
                }
            }
        }
        
        // Scan Status Overlay
        androidx.compose.material3.Surface(
            color = Color.Black.copy(alpha = 0.6f),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.TopCenter)
                .padding(top = 16.dp)
        ) {
            androidx.compose.material3.Text(
                text = scanStatus,
                color = Color.White,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}
