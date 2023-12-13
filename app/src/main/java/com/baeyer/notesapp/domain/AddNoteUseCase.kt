package com.baeyer.notesapp.domain

import com.baeyer.notesapp.data.model.Note
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend fun addNote(note: Note) {
        repository.addNote(note)
    }
}