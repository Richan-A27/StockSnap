package com.stocksnap.backup;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.stocksnap.data.database.AppDatabase;
import kotlinx.coroutines.Dispatchers;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0010"}, d2 = {"Lcom/stocksnap/backup/RestoreManager;", "", "context", "Landroid/content/Context;", "db", "Lcom/stocksnap/data/database/AppDatabase;", "driveService", "Lcom/stocksnap/backup/DriveService;", "(Landroid/content/Context;Lcom/stocksnap/data/database/AppDatabase;Lcom/stocksnap/backup/DriveService;)V", "downloadAndRestore", "Lkotlin/Result;", "", "account", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "downloadAndRestore-gIAlu-s", "(Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class RestoreManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.database.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.backup.DriveService driveService = null;
    
    public RestoreManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.AppDatabase db, @org.jetbrains.annotations.NotNull()
    com.stocksnap.backup.DriveService driveService) {
        super();
    }
}