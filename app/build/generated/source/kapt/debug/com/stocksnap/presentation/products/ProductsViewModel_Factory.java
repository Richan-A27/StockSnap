package com.stocksnap.presentation.products;

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
public final class ProductsViewModel_Factory implements Factory<ProductsViewModel> {
  private final Provider<ProductRepository> repositoryProvider;

  public ProductsViewModel_Factory(Provider<ProductRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ProductsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ProductsViewModel_Factory create(Provider<ProductRepository> repositoryProvider) {
    return new ProductsViewModel_Factory(repositoryProvider);
  }

  public static ProductsViewModel newInstance(ProductRepository repository) {
    return new ProductsViewModel(repository);
  }
}
