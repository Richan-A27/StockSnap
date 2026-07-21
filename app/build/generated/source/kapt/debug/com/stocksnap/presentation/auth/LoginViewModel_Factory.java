package com.stocksnap.presentation.auth;

import com.stocksnap.data.repository.AuthRepository;
import com.stocksnap.util.NetworkConnectivityObserver;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<NetworkConnectivityObserver> networkConnectivityObserverProvider;

  public LoginViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<NetworkConnectivityObserver> networkConnectivityObserverProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.networkConnectivityObserverProvider = networkConnectivityObserverProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(authRepositoryProvider.get(), networkConnectivityObserverProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<NetworkConnectivityObserver> networkConnectivityObserverProvider) {
    return new LoginViewModel_Factory(authRepositoryProvider, networkConnectivityObserverProvider);
  }

  public static LoginViewModel newInstance(AuthRepository authRepository,
      NetworkConnectivityObserver networkConnectivityObserver) {
    return new LoginViewModel(authRepository, networkConnectivityObserver);
  }
}
