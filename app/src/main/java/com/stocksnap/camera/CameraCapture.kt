package com.stocksnap.camera

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    onImageCaptureReady: (ImageCapture) -> Unit,
    onError: (Exception) -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = context as? LifecycleOwner
    val previewView = remember { PreviewView(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    var cameraControl by remember { mutableStateOf<androidx.camera.core.CameraControl?>(null) }
    var cameraInfo by remember { mutableStateOf<androidx.camera.core.CameraInfo?>(null) }

    DisposableEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val mainExecutor = ContextCompat.getMainExecutor(context)
        cameraProviderFuture.addListener(
            {
                try {
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    cameraProvider.unbindAll()
                    val camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner ?: throw IllegalStateException("LifecycleOwner not found"),
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageCapture
                    )
                    cameraControl = camera.cameraControl
                    cameraInfo = camera.cameraInfo
                    onImageCaptureReady(imageCapture)
                } catch (e: Exception) {
                    Log.e("CameraCapture", "Failed to bind camera", e)
                    onError(e)
                }
            },
            mainExecutor
        )

        onDispose {
            if (cameraProviderFuture.isDone) {
                cameraProviderFuture.get().unbindAll()
            }
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    val currentZoom = cameraInfo?.zoomState?.value?.zoomRatio ?: 1f
                    cameraControl?.setZoomRatio(
                        (currentZoom * zoom).coerceIn(
                            cameraInfo?.zoomState?.value?.minZoomRatio ?: 1f,
                            cameraInfo?.zoomState?.value?.maxZoomRatio ?: 10f
                        )
                    )
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val factory = previewView.meteringPointFactory
                        val point = factory.createPoint(offset.x, offset.y)
                        val action = androidx.camera.core.FocusMeteringAction.Builder(point, androidx.camera.core.FocusMeteringAction.FLAG_AF).build()
                        cameraControl?.startFocusAndMetering(action)
                    }
                )
            }
    )
}
