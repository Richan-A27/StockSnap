package com.stocksnap.utils;

import com.stocksnap.data.database.ProductDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MigrationUtility_Factory implements Factory<MigrationUtility> {
  private final Provider<ProductDao> daoProvider;

  public MigrationUtility_Factory(Provider<ProductDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public MigrationUtility get() {
    return newInstance(daoProvider.get());
  }

  public static MigrationUtility_Factory create(Provider<ProductDao> daoProvider) {
    return new MigrationUtility_Factory(daoProvider);
  }

  public static MigrationUtility newInstance(ProductDao dao) {
    return new MigrationUtility(dao);
  }
}
