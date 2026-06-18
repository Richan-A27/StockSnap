package com.stocksnap.presentation.settings;

import com.stocksnap.backup.BackupManager;
import com.stocksnap.backup.DriveService;
import com.stocksnap.backup.RestoreManager;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<ProductRepository> repositoryProvider;

  private final Provider<BackupManager> backupManagerProvider;

  private final Provider<RestoreManager> restoreManagerProvider;

  private final Provider<DriveService> driveServiceProvider;

  public SettingsViewModel_Factory(Provider<ProductRepository> repositoryProvider,
      Provider<BackupManager> backupManagerProvider,
      Provider<RestoreManager> restoreManagerProvider,
      Provider<DriveService> driveServiceProvider) {
    this.repositoryProvider = repositoryProvider;
    this.backupManagerProvider = backupManagerProvider;
    this.restoreManagerProvider = restoreManagerProvider;
    this.driveServiceProvider = driveServiceProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(repositoryProvider.get(), backupManagerProvider.get(), restoreManagerProvider.get(), driveServiceProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<ProductRepository> repositoryProvider,
      Provider<BackupManager> backupManagerProvider,
      Provider<RestoreManager> restoreManagerProvider,
      Provider<DriveService> driveServiceProvider) {
    return new SettingsViewModel_Factory(repositoryProvider, backupManagerProvider, restoreManagerProvider, driveServiceProvider);
  }

  public static SettingsViewModel newInstance(ProductRepository repository,
      BackupManager backupManager, RestoreManager restoreManager, DriveService driveService) {
    return new SettingsViewModel(repository, backupManager, restoreManager, driveService);
  }
}
