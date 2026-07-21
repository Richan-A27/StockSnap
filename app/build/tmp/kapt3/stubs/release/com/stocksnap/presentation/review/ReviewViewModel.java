package com.stocksnap.presentation.review;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.stocksnap.data.database.Product;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.ocr.MLKitProcessor;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u000e\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\rR\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0019\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/stocksnap/presentation/review/ReviewViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "mlKitProcessor", "Lcom/stocksnap/ocr/MLKitProcessor;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/stocksnap/data/repository/ProductRepository;Lcom/stocksnap/ocr/MLKitProcessor;)V", "_previousMrp", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_product", "Lcom/stocksnap/data/database/Product;", "previousMrp", "Lkotlinx/coroutines/flow/StateFlow;", "getPreviousMrp", "()Lkotlinx/coroutines/flow/StateFlow;", "product", "getProduct", "loadProduct", "", "id", "", "updateProduct", "updated", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ReviewViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.SavedStateHandle savedStateHandle = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.ocr.MLKitProcessor mlKitProcessor = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.stocksnap.data.database.Product> _product = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.database.Product> product = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Double> _previousMrp = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Double> previousMrp = null;
    
    @javax.inject.Inject()
    public ReviewViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository, @org.jetbrains.annotations.NotNull()
    com.stocksnap.ocr.MLKitProcessor mlKitProcessor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.database.Product> getProduct() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Double> getPreviousMrp() {
        return null;
    }
    
    private final void loadProduct(long id) {
    }
    
    public final void updateProduct(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product updated) {
    }
}