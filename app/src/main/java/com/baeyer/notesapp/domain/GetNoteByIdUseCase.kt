package com.baeyer.notesapp.domain

import com.baeyer.notesapp.data.model.Note
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository,
) {
    suspend fun getNoteById(id: Int): Note {
        return repository.getNoteById(id)
    }
}