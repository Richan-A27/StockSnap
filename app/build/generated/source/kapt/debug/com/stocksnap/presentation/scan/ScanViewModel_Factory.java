package com.stocksnap.presentation.scan;

import com.stocksnap.domain.usecase.SaveProductUseCase;
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
  private final Provider<SaveProductUseCase> saveProductUseCaseProvider;

  public ScanViewModel_Factory(Provider<SaveProductUseCase> saveProductUseCaseProvider) {
    this.saveProductUseCaseProvider = saveProductUseCaseProvider;
  }

  @Override
  public ScanViewModel get() {
    return newInstance(saveProductUseCaseProvider.get());
  }

  public static ScanViewModel_Factory create(
      Provider<SaveProductUseCase> saveProductUseCaseProvider) {
    return new ScanViewModel_Factory(saveProductUseCaseProvider);
  }

  public static ScanViewModel newInstance(SaveProductUseCase saveProductUseCase) {
    return new ScanViewModel(saveProductUseCase);
  }
}
