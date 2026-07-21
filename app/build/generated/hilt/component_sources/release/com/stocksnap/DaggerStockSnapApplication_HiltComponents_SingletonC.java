package com.stocksnap;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkManager;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.stocksnap.data.database.AppDatabase;
import com.stocksnap.data.database.ProductDao;
import com.stocksnap.data.repository.AuthRepository;
import com.stocksnap.data.repository.ProductRepository;
import com.stocksnap.di.AppModule;
import com.stocksnap.di.AppModule_ProvideAuthRepositoryFactory;
import com.stocksnap.di.AppModule_ProvideDatabaseFactory;
import com.stocksnap.di.AppModule_ProvideFirebaseAuthFactory;
import com.stocksnap.di.AppModule_ProvideFirebaseFirestoreFactory;
import com.stocksnap.di.AppModule_ProvideFirebaseStorageFactory;
import com.stocksnap.di.AppModule_ProvideNetworkConnectivityObserverFactory;
import com.stocksnap.di.AppModule_ProvideProductDaoFactory;
import com.stocksnap.di.AppModule_ProvideProductRepositoryFactory;
import com.stocksnap.di.AppModule_ProvideWorkManagerFactory;
import com.stocksnap.ocr.MLKitProcessor;
import com.stocksnap.presentation.activity.ActivityLogsViewModel;
import com.stocksnap.presentation.activity.ActivityLogsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.admin.UserManagementViewModel;
import com.stocksnap.presentation.admin.UserManagementViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.auth.LoginViewModel;
import com.stocksnap.presentation.auth.LoginViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.auth.SplashViewModel;
import com.stocksnap.presentation.auth.SplashViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.dashboard.DashboardViewModel;
import com.stocksnap.presentation.dashboard.DashboardViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.details.DetailsViewModel;
import com.stocksnap.presentation.details.DetailsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.edit.EditArrivalViewModel;
import com.stocksnap.presentation.edit.EditArrivalViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.edit.EditProductViewModel;
import com.stocksnap.presentation.edit.EditProductViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.history.HistoryViewModel;
import com.stocksnap.presentation.history.HistoryViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.products.ProductsViewModel;
import com.stocksnap.presentation.products.ProductsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.review.ReviewViewModel;
import com.stocksnap.presentation.review.ReviewViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.scan.ScanViewModel;
import com.stocksnap.presentation.scan.ScanViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.presentation.settings.SettingsViewModel;
import com.stocksnap.presentation.settings.SettingsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.stocksnap.ui.MainActivity;
import com.stocksnap.ui.MainActivity_MembersInjector;
import com.stocksnap.util.NetworkConnectivityObserver;
import com.stocksnap.utils.MigrationUtility;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class DaggerStockSnapApplication_HiltComponents_SingletonC {
  private DaggerStockSnapApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder appModule(AppModule appModule) {
      Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule(
        HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule) {
      Preconditions.checkNotNull(hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule);
      return this;
    }

    public StockSnapApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements StockSnapApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public StockSnapApplication_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonCImpl);
    }
  }

  private static final class ActivityCBuilder implements StockSnapApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public StockSnapApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements StockSnapApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public StockSnapApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements StockSnapApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public StockSnapApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements StockSnapApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public StockSnapApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements StockSnapApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public StockSnapApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements StockSnapApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public StockSnapApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends StockSnapApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends StockSnapApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends StockSnapApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends StockSnapApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(ActivityLogsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), DashboardViewModel_HiltModules_KeyModule_ProvideFactory.provide(), DetailsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), EditArrivalViewModel_HiltModules_KeyModule_ProvideFactory.provide(), EditProductViewModel_HiltModules_KeyModule_ProvideFactory.provide(), HistoryViewModel_HiltModules_KeyModule_ProvideFactory.provide(), LoginViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ProductsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ReviewViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ScanViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SettingsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SplashViewModel_HiltModules_KeyModule_ProvideFactory.provide(), UserManagementViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectAuthRepository(instance, singletonCImpl.provideAuthRepositoryProvider.get());
      MainActivity_MembersInjector.injectProductRepository(instance, singletonCImpl.provideProductRepositoryProvider.get());
      MainActivity_MembersInjector.injectMigrationUtility(instance, singletonCImpl.migrationUtilityProvider.get());
      MainActivity_MembersInjector.injectNetworkConnectivityObserver(instance, singletonCImpl.provideNetworkConnectivityObserverProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends StockSnapApplication_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<ActivityLogsViewModel> activityLogsViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<DetailsViewModel> detailsViewModelProvider;

    private Provider<EditArrivalViewModel> editArrivalViewModelProvider;

    private Provider<EditProductViewModel> editProductViewModelProvider;

    private Provider<HistoryViewModel> historyViewModelProvider;

    private Provider<LoginViewModel> loginViewModelProvider;

    private Provider<ProductsViewModel> productsViewModelProvider;

    private Provider<ReviewViewModel> reviewViewModelProvider;

    private Provider<ScanViewModel> scanViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private Provider<UserManagementViewModel> userManagementViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.activityLogsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.detailsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.editArrivalViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.editProductViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.historyViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.productsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.reviewViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.scanViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.userManagementViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, Provider<ViewModel>>builderWithExpectedSize(13).put("com.stocksnap.presentation.activity.ActivityLogsViewModel", ((Provider) activityLogsViewModelProvider)).put("com.stocksnap.presentation.dashboard.DashboardViewModel", ((Provider) dashboardViewModelProvider)).put("com.stocksnap.presentation.details.DetailsViewModel", ((Provider) detailsViewModelProvider)).put("com.stocksnap.presentation.edit.EditArrivalViewModel", ((Provider) editArrivalViewModelProvider)).put("com.stocksnap.presentation.edit.EditProductViewModel", ((Provider) editProductViewModelProvider)).put("com.stocksnap.presentation.history.HistoryViewModel", ((Provider) historyViewModelProvider)).put("com.stocksnap.presentation.auth.LoginViewModel", ((Provider) loginViewModelProvider)).put("com.stocksnap.presentation.products.ProductsViewModel", ((Provider) productsViewModelProvider)).put("com.stocksnap.presentation.review.ReviewViewModel", ((Provider) reviewViewModelProvider)).put("com.stocksnap.presentation.scan.ScanViewModel", ((Provider) scanViewModelProvider)).put("com.stocksnap.presentation.settings.SettingsViewModel", ((Provider) settingsViewModelProvider)).put("com.stocksnap.presentation.auth.SplashViewModel", ((Provider) splashViewModelProvider)).put("com.stocksnap.presentation.admin.UserManagementViewModel", ((Provider) userManagementViewModelProvider)).build();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.stocksnap.presentation.activity.ActivityLogsViewModel 
          return (T) new ActivityLogsViewModel(singletonCImpl.provideProductRepositoryProvider.get());

          case 1: // com.stocksnap.presentation.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.provideProductRepositoryProvider.get(), singletonCImpl.provideAuthRepositoryProvider.get());

          case 2: // com.stocksnap.presentation.details.DetailsViewModel 
          return (T) new DetailsViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.provideProductRepositoryProvider.get());

          case 3: // com.stocksnap.presentation.edit.EditArrivalViewModel 
          return (T) new EditArrivalViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.provideProductRepositoryProvider.get());

          case 4: // com.stocksnap.presentation.edit.EditProductViewModel 
          return (T) new EditProductViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.provideProductRepositoryProvider.get());

          case 5: // com.stocksnap.presentation.history.HistoryViewModel 
          return (T) new HistoryViewModel(singletonCImpl.provideProductRepositoryProvider.get());

          case 6: // com.stocksnap.presentation.auth.LoginViewModel 
          return (T) new LoginViewModel(singletonCImpl.provideAuthRepositoryProvider.get(), singletonCImpl.provideNetworkConnectivityObserverProvider.get());

          case 7: // com.stocksnap.presentation.products.ProductsViewModel 
          return (T) new ProductsViewModel(singletonCImpl.provideProductRepositoryProvider.get());

          case 8: // com.stocksnap.presentation.review.ReviewViewModel 
          return (T) new ReviewViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.provideProductRepositoryProvider.get(), singletonCImpl.mLKitProcessorProvider.get());

          case 9: // com.stocksnap.presentation.scan.ScanViewModel 
          return (T) new ScanViewModel(singletonCImpl.provideProductRepositoryProvider.get(), singletonCImpl.mLKitProcessorProvider.get());

          case 10: // com.stocksnap.presentation.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.provideAuthRepositoryProvider.get(), singletonCImpl.provideProductRepositoryProvider.get());

          case 11: // com.stocksnap.presentation.auth.SplashViewModel 
          return (T) new SplashViewModel(singletonCImpl.provideAuthRepositoryProvider.get());

          case 12: // com.stocksnap.presentation.admin.UserManagementViewModel 
          return (T) new UserManagementViewModel(singletonCImpl.provideProductRepositoryProvider.get(), singletonCImpl.provideAuthRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends StockSnapApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends StockSnapApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends StockSnapApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AppDatabase> provideDatabaseProvider;

    private Provider<FirebaseFirestore> provideFirebaseFirestoreProvider;

    private Provider<FirebaseStorage> provideFirebaseStorageProvider;

    private Provider<FirebaseAuth> provideFirebaseAuthProvider;

    private Provider<AuthRepository> provideAuthRepositoryProvider;

    private Provider<WorkManager> provideWorkManagerProvider;

    private Provider<ProductRepository> provideProductRepositoryProvider;

    private Provider<MigrationUtility> migrationUtilityProvider;

    private Provider<NetworkConnectivityObserver> provideNetworkConnectivityObserverProvider;

    private Provider<MLKitProcessor> mLKitProcessorProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private ProductDao productDao() {
      return AppModule_ProvideProductDaoFactory.provideProductDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 0));
      this.provideFirebaseFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 1));
      this.provideFirebaseStorageProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseStorage>(singletonCImpl, 2));
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 4));
      this.provideAuthRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 3));
      this.provideWorkManagerProvider = DoubleCheck.provider(new SwitchingProvider<WorkManager>(singletonCImpl, 6));
      this.provideProductRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ProductRepository>(singletonCImpl, 5));
      this.migrationUtilityProvider = DoubleCheck.provider(new SwitchingProvider<MigrationUtility>(singletonCImpl, 7));
      this.provideNetworkConnectivityObserverProvider = DoubleCheck.provider(new SwitchingProvider<NetworkConnectivityObserver>(singletonCImpl, 8));
      this.mLKitProcessorProvider = DoubleCheck.provider(new SwitchingProvider<MLKitProcessor>(singletonCImpl, 9));
    }

    @Override
    public void injectStockSnapApplication(StockSnapApplication stockSnapApplication) {
    }

    @Override
    public AppDatabase appDatabase() {
      return provideDatabaseProvider.get();
    }

    @Override
    public FirebaseFirestore firestore() {
      return provideFirebaseFirestoreProvider.get();
    }

    @Override
    public FirebaseStorage storage() {
      return provideFirebaseStorageProvider.get();
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.stocksnap.data.database.AppDatabase 
          return (T) AppModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.google.firebase.firestore.FirebaseFirestore 
          return (T) AppModule_ProvideFirebaseFirestoreFactory.provideFirebaseFirestore();

          case 2: // com.google.firebase.storage.FirebaseStorage 
          return (T) AppModule_ProvideFirebaseStorageFactory.provideFirebaseStorage();

          case 3: // com.stocksnap.data.repository.AuthRepository 
          return (T) AppModule_ProvideAuthRepositoryFactory.provideAuthRepository(singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.provideFirebaseFirestoreProvider.get());

          case 4: // com.google.firebase.auth.FirebaseAuth 
          return (T) AppModule_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 5: // com.stocksnap.data.repository.ProductRepository 
          return (T) AppModule_ProvideProductRepositoryFactory.provideProductRepository(singletonCImpl.productDao(), singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.provideFirebaseFirestoreProvider.get(), singletonCImpl.provideFirebaseStorageProvider.get(), singletonCImpl.provideWorkManagerProvider.get());

          case 6: // androidx.work.WorkManager 
          return (T) AppModule_ProvideWorkManagerFactory.provideWorkManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.stocksnap.utils.MigrationUtility 
          return (T) new MigrationUtility(singletonCImpl.productDao());

          case 8: // com.stocksnap.util.NetworkConnectivityObserver 
          return (T) AppModule_ProvideNetworkConnectivityObserverFactory.provideNetworkConnectivityObserver(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.stocksnap.ocr.MLKitProcessor 
          return (T) new MLKitProcessor(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
