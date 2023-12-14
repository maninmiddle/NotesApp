package com.baeyer.notesapp.presentation.main;

import com.baeyer.notesapp.domain.AddNoteUseCase;
import com.baeyer.notesapp.domain.DeleteNoteUseCase;
import com.baeyer.notesapp.domain.EditNoteUseCase;
import com.baeyer.notesapp.domain.GetNotesUseCase;
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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<AddNoteUseCase> addNoteUseCaseProvider;

  private final Provider<EditNoteUseCase> editNoteUseCaseProvider;

  private final Provider<GetNotesUseCase> getNotesUseCaseProvider;

  private final Provider<DeleteNoteUseCase> deleteNoteUseCaseProvider;

  public MainViewModel_Factory(Provider<AddNoteUseCase> addNoteUseCaseProvider,
      Provider<EditNoteUseCase> editNoteUseCaseProvider,
      Provider<GetNotesUseCase> getNotesUseCaseProvider,
      Provider<DeleteNoteUseCase> deleteNoteUseCaseProvider) {
    this.addNoteUseCaseProvider = addNoteUseCaseProvider;
    this.editNoteUseCaseProvider = editNoteUseCaseProvider;
    this.getNotesUseCaseProvider = getNotesUseCaseProvider;
    this.deleteNoteUseCaseProvider = deleteNoteUseCaseProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(addNoteUseCaseProvider.get(), editNoteUseCaseProvider.get(), getNotesUseCaseProvider.get(), deleteNoteUseCaseProvider.get());
  }

  public static MainViewModel_Factory create(Provider<AddNoteUseCase> addNoteUseCaseProvider,
      Provider<EditNoteUseCase> editNoteUseCaseProvider,
      Provider<GetNotesUseCase> getNotesUseCaseProvider,
      Provider<DeleteNoteUseCase> deleteNoteUseCaseProvider) {
    return new MainViewModel_Factory(addNoteUseCaseProvider, editNoteUseCaseProvider, getNotesUseCaseProvider, deleteNoteUseCaseProvider);
  }

  public static MainViewModel newInstance(AddNoteUseCase addNoteUseCase,
      EditNoteUseCase editNoteUseCase, GetNotesUseCase getNotesUseCase,
      DeleteNoteUseCase deleteNoteUseCase) {
    return new MainViewModel(addNoteUseCase, editNoteUseCase, getNotesUseCase, deleteNoteUseCase);
  }
}
