package com.stocksnap.presentation.activity;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.rounded.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.stocksnap.data.model.ActivityLog;
import java.text.SimpleDateFormat;
import java.util.*;
import android.text.format.DateUtils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a \u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\"\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\nH\u0002\u001a\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u00a8\u0006\u0012"}, d2 = {"ActivityLogCard", "", "log", "Lcom/stocksnap/data/model/ActivityLog;", "ActivityLogsScreen", "onBack", "Lkotlin/Function0;", "viewModel", "Lcom/stocksnap/presentation/activity/ActivityLogsViewModel;", "getActionDescription", "", "getActivityIconAndColors", "Lkotlin/Triple;", "Landroidx/compose/ui/graphics/Color;", "actionType", "getRelativeTime", "timeInMillis", "", "app_debug"})
public final class ActivityLogsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ActivityLogsScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.stocksnap.presentation.activity.ActivityLogsViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ActivityLogCard(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.ActivityLog log) {
    }
    
    private static final kotlin.Triple<androidx.compose.ui.graphics.Color, java.lang.String, androidx.compose.ui.graphics.Color> getActivityIconAndColors(java.lang.String actionType) {
        return null;
    }
    
    private static final java.lang.String getActionDescription(com.stocksnap.data.model.ActivityLog log) {
        return null;
    }
    
    private static final java.lang.String getRelativeTime(long timeInMillis) {
        return null;
    }
}