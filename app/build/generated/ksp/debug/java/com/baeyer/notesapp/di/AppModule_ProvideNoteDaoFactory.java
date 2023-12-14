package com.baeyer.notesapp.di;

import com.baeyer.notesapp.data.db.AppDatabase;
import com.baeyer.notesapp.data.db.dao.NoteDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideNoteDaoFactory implements Factory<NoteDao> {
  private final Provider<AppDatabase> appDatabaseProvider;

  public AppModule_ProvideNoteDaoFactory(Provider<AppDatabase> appDatabaseProvider) {
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public NoteDao get() {
    return provideNoteDao(appDatabaseProvider.get());
  }

  public static AppModule_ProvideNoteDaoFactory create(Provider<AppDatabase> appDatabaseProvider) {
    return new AppModule_ProvideNoteDaoFactory(appDatabaseProvider);
  }

  public static NoteDao provideNoteDao(AppDatabase appDatabase) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNoteDao(appDatabase));
  }
}
