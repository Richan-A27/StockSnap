package com.stocksnap.backup;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import kotlinx.coroutines.Dispatchers;
import java.io.FileOutputStream;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0006\u0010\u0011\u001a\u00020\u0012J&\u0010\u0013\u001a\n \u0015*\u0004\u0018\u00010\u00140\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/stocksnap/backup/DriveService;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "scopes", "", "", "downloadBackup", "", "account", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "targetZip", "Ljava/io/File;", "(Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDriveService", "Lcom/google/api/services/drive/Drive;", "getGoogleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "uploadBackup", "Lcom/google/api/services/drive/model/File;", "kotlin.jvm.PlatformType", "zipFile", "app_debug"})
public final class DriveService {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> scopes = null;
    
    public DriveService(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.gms.auth.api.signin.GoogleSignInClient getGoogleSignInClient() {
        return null;
    }
    
    private final com.google.api.services.drive.Drive getDriveService(com.google.android.gms.auth.api.signin.GoogleSignInAccount account) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object uploadBackup(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.auth.api.signin.GoogleSignInAccount account, @org.jetbrains.annotations.NotNull()
    java.io.File zipFile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.google.api.services.drive.model.File> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object downloadBackup(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.auth.api.signin.GoogleSignInAccount account, @org.jetbrains.annotations.NotNull()
    java.io.File targetZip, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}