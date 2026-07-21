package com.stocksnap.utils;

import android.content.Context;
import android.util.Log;
import com.stocksnap.camera.ThumbnailUtils;
import com.stocksnap.data.database.ProductDao;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Migration utility for existing products.
 * Regenerates optimized catalog images from originals
 * and marks products as pending sync so SyncWorker
 * re-uploads the compressed versions.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/stocksnap/utils/MigrationUtility;", "", "dao", "Lcom/stocksnap/data/database/ProductDao;", "(Lcom/stocksnap/data/database/ProductDao;)V", "migrateExistingImages", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_release"})
public final class MigrationUtility {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.database.ProductDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MigrationUtility";
    @org.jetbrains.annotations.NotNull()
    public static final com.stocksnap.utils.MigrationUtility.Companion Companion = null;
    
    @javax.inject.Inject()
    public MigrationUtility(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.ProductDao dao) {
        super();
    }
    
    /**
     * Scans all products that have a local frontImagePath but no catalogImagePath.
     * For each, generates an optimized catalog image and marks the product for re-sync.
     *
     * @return number of products migrated
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object migrateExistingImages(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/stocksnap/utils/MigrationUtility$Companion;", "", "()V", "TAG", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}