package com.stocksnap.camera;

import android.annotation.SuppressLint;
import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a@\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\u0018\b\u0002\u0010\u0007\u001a\u0012\u0012\b\u0012\u00060\bj\u0002`\t\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\n"}, d2 = {"CameraCapture", "", "modifier", "Landroidx/compose/ui/Modifier;", "onImageCaptureReady", "Lkotlin/Function1;", "Landroidx/camera/core/ImageCapture;", "onError", "Ljava/lang/Exception;", "Lkotlin/Exception;", "app_release"})
public final class CameraCaptureKt {
    
    @android.annotation.SuppressLint(value = {"UnsafeOptInUsageError"})
    @androidx.compose.runtime.Composable()
    public static final void CameraCapture(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.camera.core.ImageCapture, kotlin.Unit> onImageCaptureReady, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Exception, kotlin.Unit> onError) {
    }
}