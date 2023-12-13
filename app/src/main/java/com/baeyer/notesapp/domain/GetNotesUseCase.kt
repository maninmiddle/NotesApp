package com.baeyer.notesapp.domain

import com.baeyer.notesapp.data.model.Note
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend fun getNotes(): MutableList<Note> {
        return repository.getNotes()
    }
}