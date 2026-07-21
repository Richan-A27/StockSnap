package com.stocksnap.presentation.review;

import androidx.lifecycle.SavedStateHandle;
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
public final class ReviewViewModel_Factory implements Factory<ReviewViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<ProductRepository> repositoryProvider;

  private final Provider<MLKitProcessor> mlKitProcessorProvider;

  public ReviewViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProductRepository> repositoryProvider,
      Provider<MLKitProcessor> mlKitProcessorProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.repositoryProvider = repositoryProvider;
    this.mlKitProcessorProvider = mlKitProcessorProvider;
  }

  @Override
  public ReviewViewModel get() {
    return newInstance(savedStateHandleProvider.get(), repositoryProvider.get(), mlKitProcessorProvider.get());
  }

  public static ReviewViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProductRepository> repositoryProvider,
      Provider<MLKitProcessor> mlKitProcessorProvider) {
    return new ReviewViewModel_Factory(savedStateHandleProvider, repositoryProvider, mlKitProcessorProvider);
  }

  public static ReviewViewModel newInstance(SavedStateHandle savedStateHandle,
      ProductRepository repository, MLKitProcessor mlKitProcessor) {
    return new ReviewViewModel(savedStateHandle, repository, mlKitProcessor);
  }
}
