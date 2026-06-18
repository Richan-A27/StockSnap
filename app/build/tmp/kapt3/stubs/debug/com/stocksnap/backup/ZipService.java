package com.stocksnap.backup;

import kotlinx.coroutines.Dispatchers;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\t\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\rJ \u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002\u00a8\u0006\u0012"}, d2 = {"Lcom/stocksnap/backup/ZipService;", "", "()V", "createBackupZip", "", "zipFile", "Ljava/io/File;", "filesToZip", "", "baseDir", "(Ljava/io/File;Ljava/util/List;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unzipBackup", "targetDir", "(Ljava/io/File;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "zipFileOrDirectory", "out", "Ljava/util/zip/ZipOutputStream;", "file", "app_debug"})
public final class ZipService {
    @org.jetbrains.annotations.NotNull()
    public static final com.stocksnap.backup.ZipService INSTANCE = null;
    
    private ZipService() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createBackupZip(@org.jetbrains.annotations.NotNull()
    java.io.File zipFile, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.io.File> filesToZip, @org.jetbrains.annotations.NotNull()
    java.io.File baseDir, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void zipFileOrDirectory(java.util.zip.ZipOutputStream out, java.io.File file, java.io.File baseDir) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object unzipBackup(@org.jetbrains.annotations.NotNull()
    java.io.File zipFile, @org.jetbrains.annotations.NotNull()
    java.io.File targetDir, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}