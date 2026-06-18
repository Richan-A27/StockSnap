package com.stocksnap.di;

import com.stocksnap.data.database.AppDatabase;
import com.stocksnap.data.repository.ProductRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideProductRepositoryFactory implements Factory<ProductRepository> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideProductRepositoryFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ProductRepository get() {
    return provideProductRepository(dbProvider.get());
  }

  public static AppModule_ProvideProductRepositoryFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideProductRepositoryFactory(dbProvider);
  }

  public static ProductRepository provideProductRepository(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideProductRepository(db));
  }
}
