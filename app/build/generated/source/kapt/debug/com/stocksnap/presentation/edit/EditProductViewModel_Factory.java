package com.stocksnap.presentation.edit;

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
public final class EditProductViewModel_Factory implements Factory<EditProductViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<ProductRepository> repositoryProvider;

  public EditProductViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProductRepository> repositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public EditProductViewModel get() {
    return newInstance(savedStateHandleProvider.get(), repositoryProvider.get());
  }

  public static EditProductViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProductRepository> repositoryProvider) {
    return new EditProductViewModel_Factory(savedStateHandleProvider, repositoryProvider);
  }

  public static EditProductViewModel newInstance(SavedStateHandle savedStateHandle,
      ProductRepository repository) {
    return new EditProductViewModel(savedStateHandle, repository);
  }
}
