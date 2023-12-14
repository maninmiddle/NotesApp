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
public final class EditNoteUseCase_Factory implements Factory<EditNoteUseCase> {
  private final Provider<NoteRepository> repositoryProvider;

  public EditNoteUseCase_Factory(Provider<NoteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public EditNoteUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static EditNoteUseCase_Factory create(Provider<NoteRepository> repositoryProvider) {
    return new EditNoteUseCase_Factory(repositoryProvider);
  }

  public static EditNoteUseCase newInstance(NoteRepository repository) {
    return new EditNoteUseCase(repository);
  }
}
