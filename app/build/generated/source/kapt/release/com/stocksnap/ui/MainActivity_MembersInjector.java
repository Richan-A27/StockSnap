package com.stocksnap.ui;

import com.stocksnap.data.repository.AuthRepository;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.util.NetworkConnectivityObserver;
import com.stocksnap.utils.MigrationUtility;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<ProductRepository> productRepositoryProvider;

  private final Provider<MigrationUtility> migrationUtilityProvider;

  private final Provider<NetworkConnectivityObserver> networkConnectivityObserverProvider;

  public MainActivity_MembersInjector(Provider<AuthRepository> authRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<MigrationUtility> migrationUtilityProvider,
      Provider<NetworkConnectivityObserver> networkConnectivityObserverProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.productRepositoryProvider = productRepositoryProvider;
    this.migrationUtilityProvider = migrationUtilityProvider;
    this.networkConnectivityObserverProvider = networkConnectivityObserverProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<AuthRepository> authRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<MigrationUtility> migrationUtilityProvider,
      Provider<NetworkConnectivityObserver> networkConnectivityObserverProvider) {
    return new MainActivity_MembersInjector(authRepositoryProvider, productRepositoryProvider, migrationUtilityProvider, networkConnectivityObserverProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectAuthRepository(instance, authRepositoryProvider.get());
    injectProductRepository(instance, productRepositoryProvider.get());
    injectMigrationUtility(instance, migrationUtilityProvider.get());
    injectNetworkConnectivityObserver(instance, networkConnectivityObserverProvider.get());
  }

  @InjectedFieldSignature("com.stocksnap.ui.MainActivity.authRepository")
  public static void injectAuthRepository(MainActivity instance, AuthRepository authRepository) {
    instance.authRepository = authRepository;
  }

  @InjectedFieldSignature("com.stocksnap.ui.MainActivity.productRepository")
  public static void injectProductRepository(MainActivity instance,
      ProductRepository productRepository) {
    instance.productRepository = productRepository;
  }

  @InjectedFieldSignature("com.stocksnap.ui.MainActivity.migrationUtility")
  public static void injectMigrationUtility(MainActivity instance,
      MigrationUtility migrationUtility) {
    instance.migrationUtility = migrationUtility;
  }

  @InjectedFieldSignature("com.stocksnap.ui.MainActivity.networkConnectivityObserver")
  public static void injectNetworkConnectivityObserver(MainActivity instance,
      NetworkConnectivityObserver networkConnectivityObserver) {
    instance.networkConnectivityObserver = networkConnectivityObserver;
  }
}
