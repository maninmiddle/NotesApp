package com.baeyer.notesapp.di;

import android.app.Application;
import android.content.Context;
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
public final class AppModule_ProvideApplicationContextFactory implements Factory<Context> {
  private final Provider<Application> applicationProvider;

  public AppModule_ProvideApplicationContextFactory(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  @Override
  public Context get() {
    return provideApplicationContext(applicationProvider.get());
  }

  public static AppModule_ProvideApplicationContextFactory create(
      Provider<Application> applicationProvider) {
    return new AppModule_ProvideApplicationContextFactory(applicationProvider);
  }

  public static Context provideApplicationContext(Application application) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideApplicationContext(application));
  }
}
