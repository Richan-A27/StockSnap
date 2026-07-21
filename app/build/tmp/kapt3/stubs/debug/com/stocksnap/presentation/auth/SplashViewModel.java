package com.stocksnap.presentation.auth;

import androidx.lifecycle.ViewModel;
import com.stocksnap.data.repository.AuthRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharedFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2 = {"Lcom/stocksnap/presentation/auth/SplashViewModel;", "Landroidx/lifecycle/ViewModel;", "authRepository", "Lcom/stocksnap/data/repository/AuthRepository;", "(Lcom/stocksnap/data/repository/AuthRepository;)V", "_navigationRoute", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "navigationRoute", "Lkotlinx/coroutines/flow/SharedFlow;", "getNavigationRoute", "()Lkotlinx/coroutines/flow/SharedFlow;", "startTimer", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SplashViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<java.lang.String> _navigationRoute = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<java.lang.String> navigationRoute = null;
    
    @javax.inject.Inject()
    public SplashViewModel(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<java.lang.String> getNavigationRoute() {
        return null;
    }
    
    private final void startTimer() {
    }
}