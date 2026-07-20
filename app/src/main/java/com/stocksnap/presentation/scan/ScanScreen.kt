package com.stocksnap.presentation.scan

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
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
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stocksnap.camera.CameraCapture
import com.stocksnap.camera.ImageSaver
import com.stocksnap.data.database.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    onReview: () -> Unit = {},
    viewModel: ScanViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lookupResult by viewModel.lookupResult.collectAsState()
    val ocrProduct by viewModel.ocrProduct.collectAsState()
    val ocrMrp by viewModel.ocrMrp.collectAsState()
    val scanError by viewModel.scanError.collectAsState()

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

    LaunchedEffect(hasCameraPermission) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Step state machine: 
    // 0 = Scan Barcode
    // 1 = Lookup (checking catalog)
    // 2 = Capture Front Image (if new)
    // 3 = Review New Product & Create Arrival
    var stepIndex by remember { mutableStateOf(0) }
    var capturedBarcodePath by remember { mutableStateOf("") }
    var capturedFrontPath by remember { mutableStateOf("") }

    // New Product Review Fields
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var newProdName by remember { mutableStateOf("") }
    var newProdWeight by remember { mutableStateOf("") }
    var newProdCode by remember { mutableStateOf("") }
    var newProdMrp by remember { mutableStateOf("") }
    var newProdQty by remember { mutableStateOf("1") }
    var newProdSupplier by remember { mutableStateOf("") }

    LaunchedEffect(ocrProduct) {
        ocrProduct?.let { p ->
            newProdName = p.name
            newProdWeight = p.weight
            newProdCode = p.code
            newProdMrp = if (ocrMrp > 0.0) ocrMrp.toString() else ""
        }
    }

    // Handle scan errors (e.g., QR Code detected instead of EAN barcode)
    if (scanError != null) {
        AlertDialog(
            onDismissRequest = {
                viewModel.reset()
                stepIndex = 0
            },
            title = { Text("Invalid Code Scanned", fontWeight = FontWeight.Bold) },
            text = { Text(scanError ?: "Please scan the product barcode.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.reset()
                        stepIndex = 0
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Invalid Code Scanned", fontWeight = FontWeight.Bold) },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val title = when (stepIndex) {
            0 -> "Scan Barcode"
            1 -> "Checking Catalog..."
            2 -> "Capture Front Packaging Image"
            3 -> "Review & Onboard"
            else -> "Onboarding Product"
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp),
            color = Color(0xFF006E3E)
        )

        // Rendering steps
        if (stepIndex == 0) {
            // Auto Barcode Scanner
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (hasCameraPermission) {
                    com.stocksnap.camera.BarcodeScannerCamera(
                        modifier = Modifier.fillMaxSize(),
                        onBarcodeDetected = { barcode ->
                            stepIndex = 1
                            viewModel.lookupBarcodeValue(barcode) {}
                        },
                        onQrCodeRejected = { msg ->
                            showErrorDialog = true
                            errorMessage = msg
                        }
                    )
                } else {
                    Text("Camera permission is required.", color = Color.White)
                }
            }
        } else if (stepIndex == 2) {
            // Camera capture stage for Front Image
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (hasCameraPermission) {
                    CameraCapture(
                        modifier = Modifier.fillMaxSize(),
                        onImageCaptureReady = { imageCapture = it },
                        onError = { Log.e("Scan", "Camera error", it) }
                    )
                    
                    // Box overlay to help alignment
                    Box(
                        modifier = Modifier
                            .size(width = 280.dp, height = 180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                            .align(Alignment.Center)
                    )
                } else {
                    Text("Camera permission is required.", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val ic = imageCapture ?: return@Button
                    val file = ImageSaver.createImageFile(context)
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
                    val executor = ContextCompat.getMainExecutor(context)
                    
                    ic.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            capturedFrontPath = file.absolutePath
                            stepIndex = 3
                            viewModel.runOcrForNewProduct(context, file.absolutePath) {}
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Log.e("Scan", "Capture error: ${exception.message}")
                        }
                    })
                },
                enabled = hasCameraPermission,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(imageVector = Icons.Rounded.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Capture", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        } else if (stepIndex == 1) {
            // Lookup loading & branching
            when (val res = lookupResult) {
                is CatalogLookupResult.Loading -> {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF006E3E))
                    }
                }
                is CatalogLookupResult.Found -> {
                    ExistingProductArrivalView(
                        product = res.product,
                        viewModel = viewModel,
                        onSaved = onReview
                    )
                }
                is CatalogLookupResult.NotFound -> {
                    // Redirect to Capture Front Image (stepIndex = 2)
                    LaunchedEffect(Unit) {
                        stepIndex = 2
                    }
                }
                else -> {}
            }
        } else if (stepIndex == 3) {
            // Reviewing OCR extracted info for New Product
            if (lookupResult is CatalogLookupResult.Loading) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF006E3E))
                }
            } else {
                NewProductReviewForm(
                    name = newProdName,
                    onNameChange = { newProdName = it },
                    weight = newProdWeight,
                    onWeightChange = { newProdWeight = it },
                    code = newProdCode,
                    onCodeChange = { newProdCode = it },
                    mrp = newProdMrp,
                    onMrpChange = { newProdMrp = it },
                    qty = newProdQty,
                    onQtyChange = { newProdQty = it },
                    supplier = newProdSupplier,
                    onSupplierChange = { newProdSupplier = it },
                    frontImagePath = capturedFrontPath,
                    onSave = {
                        viewModel.saveNewProductAndArrival(
                            context = context,
                            name = newProdName,
                            weight = newProdWeight,
                            code = newProdCode,
                            mrp = newProdMrp.toDoubleOrNull() ?: 0.0,
                            quantity = newProdQty.toIntOrNull() ?: 1,
                            supplierName = newProdSupplier.ifEmpty { null },
                            onSaved = onReview
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ExistingProductArrivalView(
    product: Product,
    viewModel: ScanViewModel,
    onSaved: () -> Unit
) {
    var mrpVal by remember { mutableStateOf("") }
    var qtyVal by remember { mutableStateOf("1") }
    var supplierVal by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Product Found in Catalog",
            color = Color(0xFF006E3E),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Display existing info
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.frontImagePath.ifEmpty { product.frontImageUrl })
                .crossfade(true)
                .build(),
            contentDescription = "Front Image",
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF6F6F6)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = product.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Text(text = "Code: ${product.code.ifEmpty { "Not set" }}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(text = "Weight: ${product.weight.ifEmpty { "Not set" }}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(text = "Barcode: ${product.barcode}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        // MRP (Manual editable field)
        OutlinedTextField(
            value = mrpVal,
            onValueChange = { mrpVal = it },
            label = { Text("MRP (₹)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Quantity
        OutlinedTextField(
            value = qtyVal,
            onValueChange = { qtyVal = it.filter(Char::isDigit) },
            label = { Text("Quantity Received") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Supplier (Optional)
        OutlinedTextField(
            value = supplierVal,
            onValueChange = { supplierVal = it },
            label = { Text("Supplier Name (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.createArrivalForExisting(
                    product = product,
                    mrp = mrpVal.toDoubleOrNull() ?: 0.0,
                    quantity = qtyVal.toIntOrNull() ?: 1,
                    supplierName = supplierVal.ifEmpty { null },
                    onSaved = onSaved
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Create Arrival Record", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun NewProductReviewForm(
    name: String,
    onNameChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    code: String,
    onCodeChange: (String) -> Unit,
    mrp: String,
    onMrpChange: (String) -> Unit,
    qty: String,
    onQtyChange: (String) -> Unit,
    supplier: String,
    onSupplierChange: (String) -> Unit,
    frontImagePath: String,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "New Product Review",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF006E3E)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(frontImagePath)
                .crossfade(true)
                .build(),
            contentDescription = "New Product Image",
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF6F6F6)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fields
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = code,
            onValueChange = onCodeChange,
            label = { Text("Product Code (Internal)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = onWeightChange,
            label = { Text("Weight (e.g. 50g, 500ml, 1L)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = mrp,
            onValueChange = onMrpChange,
            label = { Text("MRP (₹)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = qty,
            onValueChange = { onQtyChange(it.filter(Char::isDigit)) },
            label = { Text("Quantity Received") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = supplier,
            onValueChange = onSupplierChange,
            label = { Text("Supplier Name (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006E3E)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Create Product & Arrival", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
