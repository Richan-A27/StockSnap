package com.stocksnap.presentation.details;

import androidx.lifecycle.SavedStateHandle;
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
public final class DetailsViewModel_Factory implements Factory<DetailsViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<ProductRepository> repositoryProvider;

  public DetailsViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProductRepository> repositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DetailsViewModel get() {
    return newInstance(savedStateHandleProvider.get(), repositoryProvider.get());
  }

  public static DetailsViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProductRepository> repositoryProvider) {
    return new DetailsViewModel_Factory(savedStateHandleProvider, repositoryProvider);
  }

  public static DetailsViewModel newInstance(SavedStateHandle savedStateHandle,
      ProductRepository repository) {
    return new DetailsViewModel(savedStateHandle, repository);
  }
}
