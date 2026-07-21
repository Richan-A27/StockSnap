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
public final class EditArrivalViewModel_Factory implements Factory<EditArrivalViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<ProductRepository> repositoryProvider;

  public EditArrivalViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProductRepository> repositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public EditArrivalViewModel get() {
    return newInstance(savedStateHandleProvider.get(), repositoryProvider.get());
  }

  public static EditArrivalViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProductRepository> repositoryProvider) {
    return new EditArrivalViewModel_Factory(savedStateHandleProvider, repositoryProvider);
  }

  public static EditArrivalViewModel newInstance(SavedStateHandle savedStateHandle,
      ProductRepository repository) {
    return new EditArrivalViewModel(savedStateHandle, repository);
  }
}
