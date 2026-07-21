package com.stocksnap.presentation.activity;

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
public final class ActivityLogsViewModel_Factory implements Factory<ActivityLogsViewModel> {
  private final Provider<ProductRepository> repositoryProvider;

  public ActivityLogsViewModel_Factory(Provider<ProductRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ActivityLogsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ActivityLogsViewModel_Factory create(
      Provider<ProductRepository> repositoryProvider) {
    return new ActivityLogsViewModel_Factory(repositoryProvider);
  }

  public static ActivityLogsViewModel newInstance(ProductRepository repository) {
    return new ActivityLogsViewModel(repository);
  }
}
