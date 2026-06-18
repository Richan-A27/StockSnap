package com.stocksnap.presentation.scan;

import android.Manifest;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import com.stocksnap.camera.ImageSaver;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.compose.foundation.layout.*;
import androidx.compose.ui.layout.ContentScale;
import coil.request.ImageRequest;
import com.stocksnap.data.database.Product;
import com.stocksnap.domain.model.ProductStatus;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u0007\u001a(\u0010\u0005\u001a\u00020\u00012\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u000b"}, d2 = {"CaptureThumbnail", "", "label", "", "path", "ScanScreen", "onReview", "Lkotlin/Function1;", "", "viewModel", "Lcom/stocksnap/presentation/scan/ScanViewModel;", "app_debug"})
public final class ScanScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ScanScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onReview, @org.jetbrains.annotations.NotNull()
    com.stocksnap.presentation.scan.ScanViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CaptureThumbnail(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.Nullable()
    java.lang.String path) {
    }
}