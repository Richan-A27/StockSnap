package com.stocksnap.presentation.dashboard;

import android.widget.Toast;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.material.icons.Icons;
import androidx.compose.ui.text.font.FontWeight;
import coil.request.ImageRequest;
import com.stocksnap.data.model.ActivityLog;
import com.stocksnap.domain.model.ProductStatus;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\u0010\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\nH\u0007\u001aX\u0010\u000b\u001a\u00020\u00012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u001a\u0010\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0013H\u0007\u001a4\u0010\u0015\u001a\u00020\u00012\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001b\u0010\u001c\u001a\u0010\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001fH\u0007\u001a\u0010\u0010 \u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u0002\u001a\u0010\u0010!\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020#H\u0002\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006$"}, d2 = {"ActivityIcon", "", "type", "", "color", "Landroidx/compose/ui/graphics/Color;", "ActivityIcon-4WTKRHQ", "(Ljava/lang/String;J)V", "ActivityRow", "log", "Lcom/stocksnap/data/model/ActivityLog;", "DashboardScreen", "onBarcodeScan", "Lkotlin/Function0;", "onArrivalClick", "Lkotlin/Function1;", "onViewAllDeliveries", "onViewAllActivity", "viewModel", "Lcom/stocksnap/presentation/dashboard/DashboardViewModel;", "FilterSection", "StatItem", "modifier", "Landroidx/compose/ui/Modifier;", "label", "count", "", "StatItem-g2O1Hgs", "(Landroidx/compose/ui/Modifier;Ljava/lang/String;IJ)V", "StatusChip", "status", "Lcom/stocksnap/domain/model/ProductStatus;", "getActionDescription", "getRelativeTime", "timestamp", "", "app_debug"})
public final class DashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DashboardScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBarcodeScan, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onArrivalClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onViewAllDeliveries, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onViewAllActivity, @org.jetbrains.annotations.NotNull()
    com.stocksnap.presentation.dashboard.DashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StatusChip(@org.jetbrains.annotations.NotNull()
    com.stocksnap.domain.model.ProductStatus status) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ActivityRow(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.ActivityLog log) {
    }
    
    private static final java.lang.String getActionDescription(com.stocksnap.data.model.ActivityLog log) {
        return null;
    }
    
    private static final java.lang.String getRelativeTime(long timestamp) {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void FilterSection(@org.jetbrains.annotations.NotNull()
    com.stocksnap.presentation.dashboard.DashboardViewModel viewModel) {
    }
}