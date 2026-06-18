package com.stocksnap.ocr

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.stocksnap.data.database.Product
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MLKitProcessor @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun processProductImages(product: Product): Product {
        val frontResult = try {
            recognizeTextResult(product.frontImagePath)
        } catch (e: Exception) {
            null
        }

        val barcode = try {
            scanBarcode(product.barcodeImagePath) ?: product.barcode
        } catch (e: Exception) {
            product.barcode
        }

        val mrpResult = try {
            recognizeTextResult(product.mrpImagePath)
        } catch (e: Exception) {
            null
        }

        val (name, brand) = extractNameAndBrand(frontResult)
        val mrp = extractMrp(mrpResult)

        return product.copy(
            name = name ?: product.name,
            barcode = barcode.ifEmpty { product.barcode },
            mrp = mrp ?: product.mrp,
            brand = brand ?: product.brand
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

    private suspend fun scanBarcode(filePath: String): String? {
        val file = File(filePath)
        if (!file.exists()) return null
        val uri = Uri.fromFile(file)
        val image = InputImage.fromFilePath(context, uri)
        val scanner = BarcodeScanning.getClient()
        val barcodes = scanner.process(image).await()
        if (barcodes.isNotEmpty()) return barcodes[0].rawValue
        return null
    }

    private fun extractNameAndBrand(textResult: com.google.mlkit.vision.text.Text?): Pair<String?, String?> {
        if (textResult == null) return null to null
        
        val blocks = textResult.textBlocks
        if (blocks.isEmpty()) return null to null

        // Heuristic: Sort blocks by bounding box area (larger text usually = name)
        // Also look at top blocks (usually brand)
        val sortedByArea = blocks.sortedByDescending { it.boundingBox?.let { b -> b.width() * b.height() } ?: 0 }
        
        // Brand is often at the top or first block
        val topBlock = blocks.minByOrNull { it.boundingBox?.top ?: Int.MAX_VALUE }
        
        val name = sortedByArea.firstOrNull()?.text?.lines()?.firstOrNull()
        val brand = if (topBlock != null && topBlock != sortedByArea.firstOrNull()) {
            topBlock.text.lines().firstOrNull()
        } else {
            // if top block is the name, look for other small blocks at the top
            blocks.filter { it != sortedByArea.firstOrNull() }
                  .minByOrNull { it.boundingBox?.top ?: Int.MAX_VALUE }
                  ?.text?.lines()?.firstOrNull()
        }

        // Clean up common brand sentences
        val brandClean = if (brand?.contains(" ", ignoreCase = true) == true && brand.length > 20) null else brand

        return name to brandClean
    }


    private fun extractMrp(textResult: com.google.mlkit.vision.text.Text?): Double? {
        if (textResult == null) return null
        val text = textResult.text
        
        // Pattern 1: MRP followed by number
        // Pattern 2: ₹ or Rs followed by number
        // Pattern 3: Number followed by MRP
        
        val mrpRegex = "(?i)(?:MRP|M\\.R\\.P\\.?|RS\\.?|₹)\\s*(?:[:;,.\\-]?\\s*)?(\\d+[.,]?\\d*)".toRegex()
        val match = mrpRegex.find(text)
        if (match != null) {
            return match.groupValues[1].replace(",", "").toDoubleOrNull()
        }
        
        // Fallback: search for any number near currency symbols
        val priceRegex = "(?:₹|Rs|MRP)\\s*(\\d+[.,]?\\d*)".toRegex(RegexOption.IGNORE_CASE)
        val fallbackMatch = priceRegex.find(text)
        if (fallbackMatch != null) {
            return fallbackMatch.groupValues[1].replace(",", "").toDoubleOrNull()
        }

        // Final fallback: first number with decimals that looks like a price (longer than 2 digits usually)
        val simplePriceRegex = "(\\d+[.,]\\d{2})".toRegex()
        val simpleMatch = simplePriceRegex.find(text)
        return simpleMatch?.groupValues?.get(1)?.replace(",", "")?.toDoubleOrNull()
    }
}
