package com.stocksnap.presentation.settings;

import com.stocksnap.data.repository.AuthRepository;
import com.stocksnap.data.repository.ProductRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<ProductRepository> repositoryProvider;

  public SettingsViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<ProductRepository> repositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(authRepositoryProvider.get(), repositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<ProductRepository> repositoryProvider) {
    return new SettingsViewModel_Factory(authRepositoryProvider, repositoryProvider);
  }

  public static SettingsViewModel newInstance(AuthRepository authRepository,
      ProductRepository repository) {
    return new SettingsViewModel(authRepository, repository);
  }
}
