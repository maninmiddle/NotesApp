package com.baeyer.notesapp.di;

import com.baeyer.notesapp.data.db.dao.NoteDao;
import com.baeyer.notesapp.domain.NoteRepository;
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
public final class AppModule_ProvideNoteRepositoryFactory implements Factory<NoteRepository> {
  private final Provider<NoteDao> noteDaoProvider;

  public AppModule_ProvideNoteRepositoryFactory(Provider<NoteDao> noteDaoProvider) {
    this.noteDaoProvider = noteDaoProvider;
  }

  @Override
  public NoteRepository get() {
    return provideNoteRepository(noteDaoProvider.get());
  }

  public static AppModule_ProvideNoteRepositoryFactory create(Provider<NoteDao> noteDaoProvider) {
    return new AppModule_ProvideNoteRepositoryFactory(noteDaoProvider);
  }

  public static NoteRepository provideNoteRepository(NoteDao noteDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNoteRepository(noteDao));
  }
}
