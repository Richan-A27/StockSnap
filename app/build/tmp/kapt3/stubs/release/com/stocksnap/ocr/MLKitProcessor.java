package com.stocksnap.ocr;

import android.content.Context;
import dagger.hilt.android.qualifiers.ApplicationContext;
import android.net.Uri;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.stocksnap.data.database.Product;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0002J\u0014\u0010\n\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0002J\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH\u0002J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0018\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0013\u001a\u00020\u0007H\u0082@\u00a2\u0006\u0002\u0010\u0014J\u0018\u0010\u0015\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/stocksnap/ocr/MLKitProcessor;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "extractNameAndBrand", "Lkotlin/Pair;", "", "textResult", "Lcom/google/mlkit/vision/text/Text;", "extractWeight", "getFormatName", "format", "", "processProductImages", "Lcom/stocksnap/data/database/Product;", "product", "(Lcom/stocksnap/data/database/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recognizeTextResult", "filePath", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "scanBarcodeAndFilter", "app_release"})
public final class MLKitProcessor {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public MLKitProcessor(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object processProductImages(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stocksnap.data.database.Product> $completion) {
        return null;
    }
    
    private final java.lang.Object recognizeTextResult(java.lang.String filePath, kotlin.coroutines.Continuation<? super com.google.mlkit.vision.text.Text> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object scanBarcodeAndFilter(@org.jetbrains.annotations.NotNull()
    java.lang.String filePath, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.String getFormatName(int format) {
        return null;
    }
    
    private final kotlin.Pair<java.lang.String, java.lang.String> extractNameAndBrand(com.google.mlkit.vision.text.Text textResult) {
        return null;
    }
    
    private final java.lang.String extractWeight(com.google.mlkit.vision.text.Text textResult) {
        return null;
    }
}