package com.stocksnap.ocr

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.stocksnap.data.database.Product
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

class QrCodeDetectedException(message: String) : Exception(message)

@Singleton
class MLKitProcessor @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun processProductImages(product: Product): Product {
        val frontResult = try {
            recognizeTextResult(product.frontImagePath)
        } catch (e: Exception) {
            null
        }

        val barcode = try {
            scanBarcodeAndFilter(product.barcodeImagePath) ?: product.barcode
        } catch (e: Exception) {
            product.barcode
        }

        val (name, brand) = extractNameAndBrand(frontResult)
        val weight = extractWeight(frontResult)

        return product.copy(
            name = name ?: product.name,
            barcode = barcode.ifEmpty { product.barcode },
            brand = brand ?: product.brand,
            weight = weight ?: product.weight
        )
    }

    private suspend fun recognizeTextResult(filePath: String): com.google.mlkit.vision.text.Text? {
        val file = File(filePath)
        if (!file.exists()) return null
        val uri = Uri.fromFile(file)
        val image = InputImage.fromFilePath(context, uri)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        return recognizer.process(image).await()
    }

    suspend fun scanBarcodeAndFilter(filePath: String): String? {
        val file = File(filePath)
        if (!file.exists()) return null
        val uri = Uri.fromFile(file)
        val image = InputImage.fromFilePath(context, uri)

        val options = BarcodeScannerOptions.Builder()
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
        val scanner = BarcodeScanning.getClient(options)
        val barcodes = scanner.process(image).await()

        if (barcodes.isEmpty()) return null

        // Log all detected formats and values
        for (barcode in barcodes) {
            val formatName = getFormatName(barcode.format)
            val valStr = barcode.rawValue ?: ""
            android.util.Log.i("MLKitProcessor", "Detected Format: $formatName, Detected Value: $valStr")
        }

        // Prioritize allowed formats
        val allowedFormats = listOf(
            Barcode.FORMAT_EAN_13,
            Barcode.FORMAT_EAN_8,
            Barcode.FORMAT_UPC_A,
            Barcode.FORMAT_UPC_E,
            Barcode.FORMAT_CODE_128
        )
        val allowedBarcode = barcodes.firstOrNull { it.format in allowedFormats }
        if (allowedBarcode != null) {
            return allowedBarcode.rawValue
        }

        // If no allowed formats were found, check if ignored formats are present
        val ignoredFormats = listOf(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_DATA_MATRIX,
            Barcode.FORMAT_AZTEC,
            Barcode.FORMAT_PDF417
        )
        val ignoredBarcode = barcodes.firstOrNull { it.format in ignoredFormats }
        if (ignoredBarcode != null) {
            throw QrCodeDetectedException("Please scan the product barcode.")
        }

        return null
    }

    private fun getFormatName(format: Int): String {
        return when (format) {
            Barcode.FORMAT_EAN_13 -> "EAN_13"
            Barcode.FORMAT_EAN_8 -> "EAN_8"
            Barcode.FORMAT_UPC_A -> "UPC_A"
            Barcode.FORMAT_UPC_E -> "UPC_E"
            Barcode.FORMAT_CODE_128 -> "CODE_128"
            Barcode.FORMAT_QR_CODE -> "QR_CODE"
            Barcode.FORMAT_DATA_MATRIX -> "DATA_MATRIX"
            Barcode.FORMAT_AZTEC -> "AZTEC"
            Barcode.FORMAT_PDF417 -> "PDF417"
            else -> "UNKNOWN ($format)"
        }
    }

    private fun extractNameAndBrand(textResult: com.google.mlkit.vision.text.Text?): Pair<String?, String?> {
        if (textResult == null) return null to null
        
        val blocks = textResult.textBlocks
        if (blocks.isEmpty()) return null to null

        // Heuristic: Sort blocks by bounding box area (larger text usually = name)
        val sortedByArea = blocks.sortedByDescending { it.boundingBox?.let { b -> b.width() * b.height() } ?: 0 }
        
        // Brand is often at the top or first block
        val topBlock = blocks.minByOrNull { it.boundingBox?.top ?: Int.MAX_VALUE }
        
        val name = sortedByArea.firstOrNull()?.text?.lines()?.firstOrNull()
        val brand = if (topBlock != null && topBlock != sortedByArea.firstOrNull()) {
            topBlock.text.lines().firstOrNull()
        } else {
            blocks.filter { it != sortedByArea.firstOrNull() }
                  .minByOrNull { it.boundingBox?.top ?: Int.MAX_VALUE }
                  ?.text?.lines()?.firstOrNull()
        }

        val brandClean = if (brand?.contains(" ", ignoreCase = true) == true && brand.length > 20) null else brand

        return name to brandClean
    }

    private fun extractWeight(textResult: com.google.mlkit.vision.text.Text?): String? {
        if (textResult == null) return null
        val text = textResult.text
        
        val weightRegex = "(?i)\\b(\\d+(?:\\.\\d+)?)\\s*(g|kg|ml|l)\\b".toRegex()
        val match = weightRegex.find(text)
        return match?.value?.replace(" ", "")?.replace("l", "L")?.replace("Lg", "kg")
    }
}
