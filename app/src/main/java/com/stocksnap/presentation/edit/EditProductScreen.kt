package com.stocksnap.presentation.edit

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.camera.CameraCapture
import com.stocksnap.camera.ImageSaver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    onBack: () -> Unit = {},
    viewModel: EditProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val productState by viewModel.product.collectAsState()
    val product = productState ?: return

    var name by remember { mutableStateOf(product.name) }
    var weight by remember { mutableStateOf(product.weight) }
    var code by remember { mutableStateOf(product.code) }

    var updateImageMode by remember { mutableStateOf(false) }
    var capturedNewImagePath by remember { mutableStateOf<String?>(null) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .verticalScroll(rememberScrollState())
    ) {
        // Toolbar
        TopAppBar(
            title = { Text("Edit Catalog Product", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image Preview Card
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!updateImageMode) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(capturedNewImagePath ?: product.frontImagePath.ifEmpty { product.frontImageUrl })
                                .crossfade(true)
                                .build(),
                            contentDescription = "Front Image",
                            modifier = Modifier
                                .height(180.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedButton(
                            onClick = {
                                if (hasCameraPermission) {
                                    updateImageMode = true
                                } else {
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Update Product Image", color = Color(0xFF006E3E))
                        }
                    } else {
                        // Camera capture preview
                        Box(
                            modifier = Modifier
                                .height(220.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            CameraCapture(
                                modifier = Modifier.fillMaxSize(),
                                onImageCaptureReady = { imageCapture = it }
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    val ic = imageCapture ?: return@Button
                                    val file = ImageSaver.createImageFile(context)
                                    val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
                                    val executor = ContextCompat.getMainExecutor(context)
                                    
                                    ic.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
                                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                            capturedNewImagePath = file.absolutePath
                                            updateImageMode = false
                                        }
                                        override fun onError(exception: ImageCaptureException) {
                                            updateImageMode = false
                                        }
                                    })
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E))
                            ) {
                                Text("Capture Photo")
                            }
                            OutlinedButton(onClick = { updateImageMode = false }) {
                                Text("Cancel", color = Color.Gray)
                            }
                        }
                    }
                }
            }

            // Fields Card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Barcode (Locked)
                    OutlinedTextField(
                        value = product.barcode,
                        onValueChange = {},
                        label = { Text("Barcode (Non-editable)") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        leadingIcon = { Icon(imageVector = Icons.Rounded.QrCodeScanner, contentDescription = null) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Product Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Weight
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Weight (e.g. 100g, 1L, 5kg)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Code
                    OutlinedTextField(
                        value = code,
                        onValueChange = { code = it },
                        label = { Text("Product Code (Internal)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save Action
            Button(
                onClick = {
                    viewModel.saveProduct(
                        context = context,
                        name = name,
                        weight = weight,
                        code = code,
                        newFrontImagePath = capturedNewImagePath,
                        onSaved = onBack
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E))
            ) {
                Text("Save Changes", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
