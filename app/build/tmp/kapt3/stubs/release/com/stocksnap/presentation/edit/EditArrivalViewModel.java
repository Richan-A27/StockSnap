package com.stocksnap.presentation.edit;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.stocksnap.data.model.Arrival;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.domain.model.ProductStatus;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J,\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001aR\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/stocksnap/presentation/edit/EditArrivalViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/stocksnap/data/repository/ProductRepository;)V", "_arrival", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/stocksnap/data/model/Arrival;", "arrival", "Lkotlinx/coroutines/flow/StateFlow;", "getArrival", "()Lkotlinx/coroutines/flow/StateFlow;", "loadArrival", "", "arrivalId", "", "saveArrival", "mrp", "", "quantity", "", "status", "Lcom/stocksnap/domain/model/ProductStatus;", "onSaved", "Lkotlin/Function0;", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class EditArrivalViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.SavedStateHandle savedStateHandle = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.stocksnap.data.model.Arrival> _arrival = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.model.Arrival> arrival = null;
    
    @javax.inject.Inject()
    public EditArrivalViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.model.Arrival> getArrival() {
        return null;
    }
    
    private final void loadArrival(java.lang.String arrivalId) {
    }
    
    public final void saveArrival(double mrp, int quantity, @org.jetbrains.annotations.NotNull()
    com.stocksnap.domain.model.ProductStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSaved) {
    }
}