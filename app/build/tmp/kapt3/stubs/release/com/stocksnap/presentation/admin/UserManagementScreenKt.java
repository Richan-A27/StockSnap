package com.stocksnap.presentation.admin;

import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import coil.request.ImageRequest;
import com.stocksnap.data.model.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import androidx.compose.material.icons.Icons;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a\u001e\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a,\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0007\u001a*\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a \u0010\u0018\u001a\u00020\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001c"}, d2 = {"ApprovedUserCard", "", "user", "Lcom/stocksnap/data/model/User;", "count", "", "isSelf", "", "onDisable", "Lkotlin/Function0;", "DisabledUserCard", "onEnable", "PendingUserCard", "onApprove", "onReject", "RoleBadge", "role", "", "SectionHeader", "title", "color", "Landroidx/compose/ui/graphics/Color;", "SectionHeader-mxwnekA", "(Ljava/lang/String;IJ)V", "UserManagementScreen", "onBack", "viewModel", "Lcom/stocksnap/presentation/admin/UserManagementViewModel;", "app_release"})
public final class UserManagementScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void UserManagementScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.stocksnap.presentation.admin.UserManagementViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PendingUserCard(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onApprove, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onReject) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ApprovedUserCard(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.User user, int count, boolean isSelf, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDisable) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DisabledUserCard(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEnable) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RoleBadge(@org.jetbrains.annotations.NotNull()
    java.lang.String role) {
    }
}