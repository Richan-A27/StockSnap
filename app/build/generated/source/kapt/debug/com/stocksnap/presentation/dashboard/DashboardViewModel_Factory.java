package com.stocksnap.presentation.dashboard;

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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<ProductRepository> repositoryProvider;

  public DashboardViewModel_Factory(Provider<ProductRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<ProductRepository> repositoryProvider) {
    return new DashboardViewModel_Factory(repositoryProvider);
  }

  public static DashboardViewModel newInstance(ProductRepository repository) {
    return new DashboardViewModel(repository);
  }
}
