package com.stocksnap.presentation.scan

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.database.Product
import com.stocksnap.data.model.Arrival
import com.stocksnap.data.repository.ProductRepository
import com.stocksnap.ocr.MLKitProcessor
import com.stocksnap.ocr.QrCodeDetectedException
import com.stocksnap.camera.ThumbnailUtils
import com.stocksnap.domain.model.ProductStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

sealed class CatalogLookupResult {
    object Idle : CatalogLookupResult()
    object Loading : CatalogLookupResult()
    data class Found(val product: Product) : CatalogLookupResult()
    object NotFound : CatalogLookupResult()
}

sealed class DuplicateArrivalMergeState {
    object Idle : DuplicateArrivalMergeState()
    data class PendingMerge(
        val existingArrival: Arrival,
        val newQuantity: Int,
        val newSupplier: String?,
        val onSaved: () -> Unit,
        val isNewProduct: Boolean,
        val newProduct: Product? = null
    ) : DuplicateArrivalMergeState()
}

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val mlKitProcessor: MLKitProcessor
) : ViewModel() {

    private val _lookupResult = MutableStateFlow<CatalogLookupResult>(CatalogLookupResult.Idle)
    val lookupResult: StateFlow<CatalogLookupResult> = _lookupResult

    private val _mergeState = MutableStateFlow<DuplicateArrivalMergeState>(DuplicateArrivalMergeState.Idle)
    val mergeState: StateFlow<DuplicateArrivalMergeState> = _mergeState

    private val _scanError = MutableStateFlow<String?>(null)
    val scanError: StateFlow<String?> = _scanError

    // State properties holding file paths for OCR steps
    var barcodePath: String = ""
    var frontPath: String = ""
    var scannedBarcode: String = ""

    // Prefilled OCR fields for review when creating new products
    private val _ocrProduct = MutableStateFlow<Product?>(null)
    val ocrProduct: StateFlow<Product?> = _ocrProduct

    private val _ocrMrp = MutableStateFlow<Double>(0.0)
    val ocrMrp: StateFlow<Double> = _ocrMrp

    fun lookupBarcodeValue(barcodeValue: String, onLookupDone: () -> Unit) {
        viewModelScope.launch {
            _lookupResult.value = CatalogLookupResult.Loading
            _scanError.value = null
            scannedBarcode = barcodeValue
            
            var product = repository.getProductByBarcode(scannedBarcode)
            if (product == null) {
                product = repository.getProductByBarcodeFirestore(scannedBarcode)
            }

            if (product != null) {
                _lookupResult.value = CatalogLookupResult.Found(product)
            } else {
                _lookupResult.value = CatalogLookupResult.NotFound
            }
            onLookupDone()
        }
    }

    fun lookupBarcode(context: Context, barcodeFile: String, onLookupDone: () -> Unit) {
        barcodePath = barcodeFile
        viewModelScope.launch {
            _lookupResult.value = CatalogLookupResult.Loading
            _scanError.value = null
            
            try {
                val barcode = mlKitProcessor.scanBarcodeAndFilter(barcodeFile)
                if (barcode.isNullOrEmpty()) {
                    scannedBarcode = "UNKNOWN_" + System.currentTimeMillis()
                    _lookupResult.value = CatalogLookupResult.NotFound
                } else {
                    scannedBarcode = barcode
                    // Perform direct lookup in local Room DB and Firestore catalog
                    var product = repository.getProductByBarcode(scannedBarcode)
                    if (product == null) {
                        product = repository.getProductByBarcodeFirestore(scannedBarcode)
                    }

                    if (product != null) {
                        _lookupResult.value = CatalogLookupResult.Found(product)
                    } else {
                        _lookupResult.value = CatalogLookupResult.NotFound
                    }
                }
            } catch (e: QrCodeDetectedException) {
                _scanError.value = e.message
                _lookupResult.value = CatalogLookupResult.Idle
            } catch (e: Exception) {
                scannedBarcode = "UNKNOWN_" + System.currentTimeMillis()
                _lookupResult.value = CatalogLookupResult.NotFound
            }
            onLookupDone()
        }
    }

    fun createArrivalForExisting(
        product: Product,
        mrp: Double,
        quantity: Int,
        supplierName: String?,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            val todayArrivals = repository.getArrivalsForToday(product.barcode, mrp)
            if (todayArrivals.isNotEmpty()) {
                val existingArrival = todayArrivals.first()
                _mergeState.value = DuplicateArrivalMergeState.PendingMerge(
                    existingArrival = existingArrival,
                    newQuantity = quantity,
                    newSupplier = supplierName,
                    onSaved = onSaved,
                    isNewProduct = false
                )
            } else {
                val arrival = Arrival(
                    arrivalId = UUID.randomUUID().toString(),
                    barcode = product.barcode,
                    productName = product.name,
                    mrp = mrp,
                    quantityReceived = quantity,
                    status = ProductStatus.PENDING,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    optionalSupplierName = supplierName
                )
                repository.insertArrival(arrival)
                onSaved()
            }
        }
    }

    fun runOcrForNewProduct(context: Context, frontFile: String, onOcrDone: () -> Unit) {
        frontPath = frontFile
        viewModelScope.launch {
            _lookupResult.value = CatalogLookupResult.Loading
            
            val tempProduct = Product(
                name = "",
                barcode = scannedBarcode,
                mrp = 0.0,
                frontImagePath = frontFile,
                barcodeImagePath = barcodePath,
                mrpImagePath = "",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            // Extract Name, Weight
            val processed = try {
                mlKitProcessor.processProductImages(tempProduct)
            } catch (e: Exception) {
                tempProduct
            }

            _ocrProduct.value = processed
            _ocrMrp.value = 0.0
            _lookupResult.value = CatalogLookupResult.Idle
            onOcrDone()
        }
    }

    fun saveNewProductAndArrival(
        context: Context,
        name: String,
        weight: String,
        code: String,
        mrp: Double,
        quantity: Int,
        supplierName: String?,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            val thumb = ThumbnailUtils.generateThumbnail(context, frontPath)
            
            val product = Product(
                name = name,
                barcode = scannedBarcode,
                frontImagePath = frontPath,
                thumbnailPath = thumb,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                weight = weight,
                code = code
            )

            val todayArrivals = repository.getArrivalsForToday(scannedBarcode, mrp)
            if (todayArrivals.isNotEmpty()) {
                val existingArrival = todayArrivals.first()
                _mergeState.value = DuplicateArrivalMergeState.PendingMerge(
                    existingArrival = existingArrival,
                    newQuantity = quantity,
                    newSupplier = supplierName,
                    onSaved = onSaved,
                    isNewProduct = true,
                    newProduct = product
                )
            } else {
                repository.insertProduct(product)
                val arrival = Arrival(
                    arrivalId = UUID.randomUUID().toString(),
                    barcode = scannedBarcode,
                    productName = name,
                    mrp = mrp,
                    quantityReceived = quantity,
                    status = ProductStatus.PENDING,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    optionalSupplierName = supplierName
                )
                repository.insertArrival(arrival)
                onSaved()
            }
        }
    }

    fun confirmArrivalMerge(merge: Boolean) {
        val currentState = _mergeState.value as? DuplicateArrivalMergeState.PendingMerge ?: return
        viewModelScope.launch {
            if (currentState.isNewProduct && currentState.newProduct != null) {
                repository.insertProduct(currentState.newProduct)
            }

            if (merge) {
                val oldQuantity = currentState.existingArrival.quantityReceived
                val newQty = oldQuantity + currentState.newQuantity
                
                // Concatenate suppliers
                val existingSupplier = currentState.existingArrival.optionalSupplierName
                val addedSupplier = currentState.newSupplier
                val mergedSupplier = if (!existingSupplier.isNullOrEmpty() && !addedSupplier.isNullOrEmpty() && existingSupplier != addedSupplier) {
                    "$existingSupplier, $addedSupplier"
                } else if (!existingSupplier.isNullOrEmpty()) {
                    existingSupplier
                } else {
                    addedSupplier
                }

                val updatedArrival = currentState.existingArrival.copy(
                    quantityReceived = newQty,
                    optionalSupplierName = mergedSupplier,
                    updatedAt = System.currentTimeMillis()
                )
                repository.updateArrival(updatedArrival)
                repository.logActivityFeed(
                    actionType = "PRODUCT_QUANTITY_MERGED",
                    barcode = updatedArrival.barcode,
                    productName = updatedArrival.productName
                )
            } else {
                val newArrival = Arrival(
                    arrivalId = UUID.randomUUID().toString(),
                    barcode = currentState.existingArrival.barcode,
                    productName = currentState.existingArrival.productName,
                    mrp = currentState.existingArrival.mrp,
                    quantityReceived = currentState.newQuantity,
                    status = ProductStatus.PENDING,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    optionalSupplierName = currentState.newSupplier
                )
                repository.insertArrival(newArrival)
            }
            
            _mergeState.value = DuplicateArrivalMergeState.Idle
            currentState.onSaved()
        }
    }

    fun reset() {
        _lookupResult.value = CatalogLookupResult.Idle
        barcodePath = ""
        frontPath = ""
        scannedBarcode = ""
        _ocrProduct.value = null
        _ocrMrp.value = 0.0
        _scanError.value = null
        _mergeState.value = DuplicateArrivalMergeState.Idle
    }
}
