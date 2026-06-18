package com.stocksnap.di;

import android.content.Context;
import com.stocksnap.backup.BackupManager;
import com.stocksnap.backup.DriveService;
import com.stocksnap.data.database.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideBackupManagerFactory implements Factory<BackupManager> {
  private final Provider<Context> contextProvider;

  private final Provider<AppDatabase> dbProvider;

  private final Provider<DriveService> driveServiceProvider;

  public AppModule_ProvideBackupManagerFactory(Provider<Context> contextProvider,
      Provider<AppDatabase> dbProvider, Provider<DriveService> driveServiceProvider) {
    this.contextProvider = contextProvider;
    this.dbProvider = dbProvider;
    this.driveServiceProvider = driveServiceProvider;
  }

  @Override
  public BackupManager get() {
    return provideBackupManager(contextProvider.get(), dbProvider.get(), driveServiceProvider.get());
  }

  public static AppModule_ProvideBackupManagerFactory create(Provider<Context> contextProvider,
      Provider<AppDatabase> dbProvider, Provider<DriveService> driveServiceProvider) {
    return new AppModule_ProvideBackupManagerFactory(contextProvider, dbProvider, driveServiceProvider);
  }

  public static BackupManager provideBackupManager(Context context, AppDatabase db,
      DriveService driveService) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBackupManager(context, db, driveService));
  }
}
