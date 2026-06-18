package com.stocksnap.presentation.scan;

import androidx.lifecycle.ViewModel;
import com.stocksnap.data.database.Product;
import com.stocksnap.domain.usecase.SaveProductUseCase;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J:\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\r0\u0015J\u000e\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0007R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/stocksnap/presentation/scan/ScanViewModel;", "Landroidx/lifecycle/ViewModel;", "saveProductUseCase", "Lcom/stocksnap/domain/usecase/SaveProductUseCase;", "(Lcom/stocksnap/domain/usecase/SaveProductUseCase;)V", "_currentProduct", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/stocksnap/data/database/Product;", "currentProduct", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentProduct", "()Lkotlinx/coroutines/flow/StateFlow;", "processAndSaveProduct", "", "context", "Landroid/content/Context;", "frontPath", "", "barcodePath", "mrpPath", "onSaved", "Lkotlin/Function1;", "", "setCurrentProduct", "product", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ScanViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.domain.usecase.SaveProductUseCase saveProductUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.stocksnap.data.database.Product> _currentProduct = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.database.Product> currentProduct = null;
    
    @javax.inject.Inject()
    public ScanViewModel(@org.jetbrains.annotations.NotNull()
    com.stocksnap.domain.usecase.SaveProductUseCase saveProductUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.database.Product> getCurrentProduct() {
        return null;
    }
    
    public final void setCurrentProduct(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product) {
    }
    
    public final void processAndSaveProduct(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String frontPath, @org.jetbrains.annotations.NotNull()
    java.lang.String barcodePath, @org.jetbrains.annotations.NotNull()
    java.lang.String mrpPath, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSaved) {
    }
}