package com.stocksnap.presentation.scan;

import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.ocr.MLKitProcessor;
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
public final class ScanViewModel_Factory implements Factory<ScanViewModel> {
  private final Provider<ProductRepository> repositoryProvider;

  private final Provider<MLKitProcessor> mlKitProcessorProvider;

  public ScanViewModel_Factory(Provider<ProductRepository> repositoryProvider,
      Provider<MLKitProcessor> mlKitProcessorProvider) {
    this.repositoryProvider = repositoryProvider;
    this.mlKitProcessorProvider = mlKitProcessorProvider;
  }

  @Override
  public ScanViewModel get() {
    return newInstance(repositoryProvider.get(), mlKitProcessorProvider.get());
  }

  public static ScanViewModel_Factory create(Provider<ProductRepository> repositoryProvider,
      Provider<MLKitProcessor> mlKitProcessorProvider) {
    return new ScanViewModel_Factory(repositoryProvider, mlKitProcessorProvider);
  }

  public static ScanViewModel newInstance(ProductRepository repository,
      MLKitProcessor mlKitProcessor) {
    return new ScanViewModel(repository, mlKitProcessor);
  }
}
