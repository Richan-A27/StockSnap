package com.stocksnap.data.repository;

import androidx.work.WorkManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
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
public final class ProductRepositoryImpl_Factory implements Factory<ProductRepositoryImpl> {
  private final Provider<ProductDao> daoProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseStorage> storageProvider;

  private final Provider<WorkManager> workManagerProvider;

  public ProductRepositoryImpl_Factory(Provider<ProductDao> daoProvider,
      Provider<FirebaseAuth> firebaseAuthProvider, Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseStorage> storageProvider, Provider<WorkManager> workManagerProvider) {
    this.daoProvider = daoProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.firestoreProvider = firestoreProvider;
    this.storageProvider = storageProvider;
    this.workManagerProvider = workManagerProvider;
  }

  @Override
  public ProductRepositoryImpl get() {
    return newInstance(daoProvider.get(), firebaseAuthProvider.get(), firestoreProvider.get(), storageProvider.get(), workManagerProvider.get());
  }

  public static ProductRepositoryImpl_Factory create(Provider<ProductDao> daoProvider,
      Provider<FirebaseAuth> firebaseAuthProvider, Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseStorage> storageProvider, Provider<WorkManager> workManagerProvider) {
    return new ProductRepositoryImpl_Factory(daoProvider, firebaseAuthProvider, firestoreProvider, storageProvider, workManagerProvider);
  }

  public static ProductRepositoryImpl newInstance(ProductDao dao, FirebaseAuth firebaseAuth,
      FirebaseFirestore firestore, FirebaseStorage storage, WorkManager workManager) {
    return new ProductRepositoryImpl(dao, firebaseAuth, firestore, storage, workManager);
  }
}
