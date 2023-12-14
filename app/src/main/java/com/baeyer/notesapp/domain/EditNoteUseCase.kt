package com.baeyer.notesapp.domain

import com.baeyer.notesapp.data.model.Note
import javax.inject.Inject

class EditNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend fun editNote(note: Note) {
        return repository.editNote(note)
    }
}