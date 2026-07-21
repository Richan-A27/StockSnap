package com.stocksnap.presentation.scan;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import com.stocksnap.data.database.Product;
import com.stocksnap.data.model.Arrival;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.ocr.MLKitProcessor;
import com.stocksnap.ocr.QrCodeDetectedException;
import com.stocksnap.camera.ThumbnailUtils;
import com.stocksnap.domain.model.ProductStatus;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,J6\u0010-\u001a\u00020*2\u0006\u0010.\u001a\u00020\u000f2\u0006\u0010/\u001a\u00020\r2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u00112\f\u00103\u001a\b\u0012\u0004\u0012\u00020*04J$\u00105\u001a\u00020*2\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u00112\f\u00109\u001a\b\u0012\u0004\u0012\u00020*04J\u001c\u0010:\u001a\u00020*2\u0006\u0010;\u001a\u00020\u00112\f\u00109\u001a\b\u0012\u0004\u0012\u00020*04J\u0006\u0010<\u001a\u00020*J$\u0010=\u001a\u00020*2\u0006\u00106\u001a\u0002072\u0006\u0010>\u001a\u00020\u00112\f\u0010?\u001a\b\u0012\u0004\u0012\u00020*04JN\u0010@\u001a\u00020*2\u0006\u00106\u001a\u0002072\u0006\u0010A\u001a\u00020\u00112\u0006\u0010B\u001a\u00020\u00112\u0006\u0010C\u001a\u00020\u00112\u0006\u0010/\u001a\u00020\r2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u00112\f\u00103\u001a\b\u0012\u0004\u0012\u00020*04R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\r0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0019\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001dR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001dR\u001a\u0010&\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u0014\"\u0004\b(\u0010\u0016\u00a8\u0006D"}, d2 = {"Lcom/stocksnap/presentation/scan/ScanViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "mlKitProcessor", "Lcom/stocksnap/ocr/MLKitProcessor;", "(Lcom/stocksnap/data/repository/ProductRepository;Lcom/stocksnap/ocr/MLKitProcessor;)V", "_lookupResult", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/stocksnap/presentation/scan/CatalogLookupResult;", "_mergeState", "Lcom/stocksnap/presentation/scan/DuplicateArrivalMergeState;", "_ocrMrp", "", "_ocrProduct", "Lcom/stocksnap/data/database/Product;", "_scanError", "", "barcodePath", "getBarcodePath", "()Ljava/lang/String;", "setBarcodePath", "(Ljava/lang/String;)V", "frontPath", "getFrontPath", "setFrontPath", "lookupResult", "Lkotlinx/coroutines/flow/StateFlow;", "getLookupResult", "()Lkotlinx/coroutines/flow/StateFlow;", "mergeState", "getMergeState", "ocrMrp", "getOcrMrp", "ocrProduct", "getOcrProduct", "scanError", "getScanError", "scannedBarcode", "getScannedBarcode", "setScannedBarcode", "confirmArrivalMerge", "", "merge", "", "createArrivalForExisting", "product", "mrp", "quantity", "", "supplierName", "onSaved", "Lkotlin/Function0;", "lookupBarcode", "context", "Landroid/content/Context;", "barcodeFile", "onLookupDone", "lookupBarcodeValue", "barcodeValue", "reset", "runOcrForNewProduct", "frontFile", "onOcrDone", "saveNewProductAndArrival", "name", "weight", "code", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ScanViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.ocr.MLKitProcessor mlKitProcessor = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.stocksnap.presentation.scan.CatalogLookupResult> _lookupResult = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.stocksnap.presentation.scan.CatalogLookupResult> lookupResult = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.stocksnap.presentation.scan.DuplicateArrivalMergeState> _mergeState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.stocksnap.presentation.scan.DuplicateArrivalMergeState> mergeState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _scanError = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> scanError = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String barcodePath = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String frontPath = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String scannedBarcode = "";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.stocksnap.data.database.Product> _ocrProduct = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.database.Product> ocrProduct = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Double> _ocrMrp = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Double> ocrMrp = null;
    
    @javax.inject.Inject()
    public ScanViewModel(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository, @org.jetbrains.annotations.NotNull()
    com.stocksnap.ocr.MLKitProcessor mlKitProcessor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.stocksnap.presentation.scan.CatalogLookupResult> getLookupResult() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.stocksnap.presentation.scan.DuplicateArrivalMergeState> getMergeState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getScanError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBarcodePath() {
        return null;
    }
    
    public final void setBarcodePath(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFrontPath() {
        return null;
    }
    
    public final void setFrontPath(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getScannedBarcode() {
        return null;
    }
    
    public final void setScannedBarcode(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.database.Product> getOcrProduct() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Double> getOcrMrp() {
        return null;
    }
    
    public final void lookupBarcodeValue(@org.jetbrains.annotations.NotNull()
    java.lang.String barcodeValue, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLookupDone) {
    }
    
    public final void lookupBarcode(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String barcodeFile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLookupDone) {
    }
    
    public final void createArrivalForExisting(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product, double mrp, int quantity, @org.jetbrains.annotations.Nullable()
    java.lang.String supplierName, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSaved) {
    }
    
    public final void runOcrForNewProduct(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String frontFile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOcrDone) {
    }
    
    public final void saveNewProductAndArrival(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String weight, @org.jetbrains.annotations.NotNull()
    java.lang.String code, double mrp, int quantity, @org.jetbrains.annotations.Nullable()
    java.lang.String supplierName, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSaved) {
    }
    
    public final void confirmArrivalMerge(boolean merge) {
    }
    
    public final void reset() {
    }
}