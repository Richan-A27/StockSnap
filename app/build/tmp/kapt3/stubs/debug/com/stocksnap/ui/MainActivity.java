package com.stocksnap.ui;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.tooling.preview.Preview;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material.icons.Icons;
import com.stocksnap.R;
import com.stocksnap.data.repository.AuthRepository;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.utils.MigrationUtility;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import java.util.Locale;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0014R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001f"}, d2 = {"Lcom/stocksnap/ui/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "authRepository", "Lcom/stocksnap/data/repository/AuthRepository;", "getAuthRepository", "()Lcom/stocksnap/data/repository/AuthRepository;", "setAuthRepository", "(Lcom/stocksnap/data/repository/AuthRepository;)V", "migrationUtility", "Lcom/stocksnap/utils/MigrationUtility;", "getMigrationUtility", "()Lcom/stocksnap/utils/MigrationUtility;", "setMigrationUtility", "(Lcom/stocksnap/utils/MigrationUtility;)V", "networkConnectivityObserver", "Lcom/stocksnap/util/NetworkConnectivityObserver;", "getNetworkConnectivityObserver", "()Lcom/stocksnap/util/NetworkConnectivityObserver;", "setNetworkConnectivityObserver", "(Lcom/stocksnap/util/NetworkConnectivityObserver;)V", "productRepository", "Lcom/stocksnap/data/repository/ProductRepository;", "getProductRepository", "()Lcom/stocksnap/data/repository/ProductRepository;", "setProductRepository", "(Lcom/stocksnap/data/repository/ProductRepository;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject()
    public com.stocksnap.data.repository.AuthRepository authRepository;
    @javax.inject.Inject()
    public com.stocksnap.data.repository.ProductRepository productRepository;
    @javax.inject.Inject()
    public com.stocksnap.utils.MigrationUtility migrationUtility;
    @javax.inject.Inject()
    public com.stocksnap.util.NetworkConnectivityObserver networkConnectivityObserver;
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.data.repository.AuthRepository getAuthRepository() {
        return null;
    }
    
    public final void setAuthRepository(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.AuthRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.data.repository.ProductRepository getProductRepository() {
        return null;
    }
    
    public final void setProductRepository(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.utils.MigrationUtility getMigrationUtility() {
        return null;
    }
    
    public final void setMigrationUtility(@org.jetbrains.annotations.NotNull()
    com.stocksnap.utils.MigrationUtility p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stocksnap.util.NetworkConnectivityObserver getNetworkConnectivityObserver() {
        return null;
    }
    
    public final void setNetworkConnectivityObserver(@org.jetbrains.annotations.NotNull()
    com.stocksnap.util.NetworkConnectivityObserver p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
}