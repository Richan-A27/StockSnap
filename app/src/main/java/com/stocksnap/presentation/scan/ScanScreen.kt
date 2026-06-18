package com.stocksnap.presentation.scan

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.stocksnap.camera.CameraCapture
import com.stocksnap.camera.ImageSaver
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.data.database.Product
import com.stocksnap.domain.model.ProductStatus

@Composable
fun ScanScreen(
    onReview: (Long) -> Unit = {},
    viewModel: ScanViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var frontPath by remember { mutableStateOf<String?>(null) }
    var barcodePath by remember { mutableStateOf<String?>(null) }
    var mrpPath by remember { mutableStateOf<String?>(null) }
    var stepIndex by remember { mutableStateOf(0) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }
    val steps = listOf("Front Image", "Barcode Image", "MRP Image")
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(hasCameraPermission) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Capture: ${steps[stepIndex]}")

        if (hasCameraPermission) {
            CameraCapture(
                modifier = Modifier.weight(1f),
                onImageCaptureReady = { imageCapture = it },
                onError = { Log.e("Scan", "Camera error", it) }
            )
        } else {
            Text(
                text = "Camera permission is required to capture product images.",
                modifier = Modifier.weight(1f)
            )
        }

        Button(onClick = {
            val ic = imageCapture ?: return@Button
            val file = ImageSaver.createImageFile(context)
            val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
            val executor = ContextCompat.getMainExecutor(context)
            ic.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    when (stepIndex) {
                        0 -> frontPath = file.absolutePath
                        1 -> barcodePath = file.absolutePath
                        2 -> mrpPath = file.absolutePath
                    }
                    if (stepIndex < 2) stepIndex += 1
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("Scan", "Photo capture failed: ${exception.message}")
                }
            })
        }, enabled = hasCameraPermission) {
            Text(text = "Capture")
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CaptureThumbnail(label = "Front", path = frontPath)
            CaptureThumbnail(label = "Barcode", path = barcodePath)
            CaptureThumbnail(label = "MRP", path = mrpPath)
        }

        val allCaptured = frontPath != null && barcodePath != null && mrpPath != null
        Button(onClick = {
            val fp = frontPath ?: return@Button
            val bp = barcodePath ?: return@Button
            val mp = mrpPath ?: return@Button
            
            viewModel.processAndSaveProduct(context, fp, bp, mp) { id ->
                onReview(id)
            }
        }, enabled = allCaptured) {
            Text(text = "Process & Review")
        }
    }
}

@Composable
fun CaptureThumbnail(label: String, path: String?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (path != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(path)
                        .crossfade(true)
                        .build(),
                    contentDescription = label,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_camera),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(text = label, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 4.dp))
    }
}
