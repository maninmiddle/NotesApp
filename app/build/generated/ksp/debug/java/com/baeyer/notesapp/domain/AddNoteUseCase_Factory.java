package com.baeyer.notesapp.domain;

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
public final class AddNoteUseCase_Factory implements Factory<AddNoteUseCase> {
  private final Provider<NoteRepository> repositoryProvider;

  public AddNoteUseCase_Factory(Provider<NoteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddNoteUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddNoteUseCase_Factory create(Provider<NoteRepository> repositoryProvider) {
    return new AddNoteUseCase_Factory(repositoryProvider);
  }

  public static AddNoteUseCase newInstance(NoteRepository repository) {
    return new AddNoteUseCase(repository);
  }
}
