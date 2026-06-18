package com.stocksnap.presentation.dashboard;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import coil.request.ImageRequest;
import com.stocksnap.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a8\u0010\u0000\u001a\u00020\u00012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a*\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a\u0010\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0014H\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0015"}, d2 = {"DashboardScreen", "", "onNewScan", "Lkotlin/Function0;", "onProductClick", "Lkotlin/Function1;", "", "viewModel", "Lcom/stocksnap/presentation/dashboard/DashboardViewModel;", "StatItem", "label", "", "count", "", "color", "Landroidx/compose/ui/graphics/Color;", "StatItem-mxwnekA", "(Ljava/lang/String;IJ)V", "StatusChip", "status", "Lcom/stocksnap/domain/model/ProductStatus;", "app_debug"})
public final class DashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DashboardScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNewScan, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onProductClick, @org.jetbrains.annotations.NotNull()
    com.stocksnap.presentation.dashboard.DashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StatusChip(@org.jetbrains.annotations.NotNull()
    com.stocksnap.domain.model.ProductStatus status) {
    }
}