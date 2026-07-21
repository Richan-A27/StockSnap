package com.stocksnap.presentation.settings;

import android.content.Context;
import android.os.Environment;
import androidx.lifecycle.ViewModel;
import com.stocksnap.data.repository.AuthRepository;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.data.model.User;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;
import java.io.FileWriter;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019J\u001c\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u001eR\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/stocksnap/presentation/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "authRepository", "Lcom/stocksnap/data/repository/AuthRepository;", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "(Lcom/stocksnap/data/repository/AuthRepository;Lcom/stocksnap/data/repository/ProductRepository;)V", "_exportStatus", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "backupStatus", "Lkotlinx/coroutines/flow/StateFlow;", "getBackupStatus", "()Lkotlinx/coroutines/flow/StateFlow;", "currentUser", "Lcom/stocksnap/data/model/User;", "getCurrentUser", "exportStatus", "getExportStatus", "checkAdminRole", "", "clearStatus", "", "exportActivityLogsCsv", "context", "Landroid/content/Context;", "exportArrivalsCsv", "exportProductsCsv", "signOut", "onSignedOut", "Lkotlin/Function0;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.model.User> currentUser = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _exportStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> exportStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> backupStatus = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.model.User> getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getExportStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getBackupStatus() {
        return null;
    }
    
    public final void signOut(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSignedOut) {
    }
    
    private final boolean checkAdminRole() {
        return false;
    }
    
    public final void exportProductsCsv(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void exportArrivalsCsv(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void exportActivityLogsCsv(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void clearStatus() {
    }
}