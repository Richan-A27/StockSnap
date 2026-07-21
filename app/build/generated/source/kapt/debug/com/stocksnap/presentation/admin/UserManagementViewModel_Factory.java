package com.stocksnap.presentation.admin;

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
public final class UserManagementViewModel_Factory implements Factory<UserManagementViewModel> {
  private final Provider<ProductRepository> repositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public UserManagementViewModel_Factory(Provider<ProductRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public UserManagementViewModel get() {
    return newInstance(repositoryProvider.get(), authRepositoryProvider.get());
  }

  public static UserManagementViewModel_Factory create(
      Provider<ProductRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new UserManagementViewModel_Factory(repositoryProvider, authRepositoryProvider);
  }

  public static UserManagementViewModel newInstance(ProductRepository repository,
      AuthRepository authRepository) {
    return new UserManagementViewModel(repository, authRepository);
  }
}
