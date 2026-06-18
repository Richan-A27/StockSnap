package com.stocksnap.domain.usecase;

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
public final class SaveProductUseCase_Factory implements Factory<SaveProductUseCase> {
  private final Provider<ProductRepository> repositoryProvider;

  public SaveProductUseCase_Factory(Provider<ProductRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveProductUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveProductUseCase_Factory create(Provider<ProductRepository> repositoryProvider) {
    return new SaveProductUseCase_Factory(repositoryProvider);
  }

  public static SaveProductUseCase newInstance(ProductRepository repository) {
    return new SaveProductUseCase(repository);
  }
}
