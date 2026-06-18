package com.stocksnap.presentation.settings;

import android.content.Context;
import android.os.Environment;
import androidx.lifecycle.ViewModel;
import com.stocksnap.data.repository.ProductRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;
import java.io.FileWriter;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u001cJ\u0006\u0010 \u001a\u00020\u001cJ\u000e\u0010!\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010\"\u001a\u00020\u001c2\b\u0010#\u001a\u0004\u0018\u00010\u0010J\u0006\u0010$\u001a\u00020\u001cJ\u000e\u0010%\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010&\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020\rR\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014\u00a8\u0006("}, d2 = {"Lcom/stocksnap/presentation/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "backupManager", "Lcom/stocksnap/backup/BackupManager;", "restoreManager", "Lcom/stocksnap/backup/RestoreManager;", "driveService", "Lcom/stocksnap/backup/DriveService;", "(Lcom/stocksnap/data/repository/ProductRepository;Lcom/stocksnap/backup/BackupManager;Lcom/stocksnap/backup/RestoreManager;Lcom/stocksnap/backup/DriveService;)V", "_backupStatus", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_exportStatus", "_userAccount", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "backupStatus", "Lkotlinx/coroutines/flow/StateFlow;", "getBackupStatus", "()Lkotlinx/coroutines/flow/StateFlow;", "getDriveService", "()Lcom/stocksnap/backup/DriveService;", "exportStatus", "getExportStatus", "userAccount", "getUserAccount", "checkAccount", "", "context", "Landroid/content/Context;", "clearStatus", "createBackup", "exportToCsv", "handleSignInResult", "account", "restoreBackup", "signOut", "updateBackupStatus", "status", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.backup.BackupManager backupManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.backup.RestoreManager restoreManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.backup.DriveService driveService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.google.android.gms.auth.api.signin.GoogleSignInAccount> _userAccount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.google.android.gms.auth.api.signin.GoogleSignInAccount> userAccount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _backupStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> backupStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _exportStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> exportStatus = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository, @org.jetbrains.annotations.NotNull()
    com.stocksnap.backup.BackupManager backupManager, @org.jetbrains.annotations.NotNull()
    com.stocksnap.backup.RestoreManager restoreManager, @org.jetbrains.annotations.NotNull()
    com.stocksnap.backup.DriveService driveService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.backup.DriveService getDriveService() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.google.android.gms.auth.api.signin.GoogleSignInAccount> getUserAccount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getBackupStatus() {
        return null;
    }
    
    public final void checkAccount(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void handleSignInResult(@org.jetbrains.annotations.Nullable()
    com.google.android.gms.auth.api.signin.GoogleSignInAccount account) {
    }
    
    public final void signOut(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void createBackup() {
    }
    
    public final void restoreBackup() {
    }
    
    public final void updateBackupStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getExportStatus() {
        return null;
    }
    
    public final void exportToCsv(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void clearStatus() {
    }
}