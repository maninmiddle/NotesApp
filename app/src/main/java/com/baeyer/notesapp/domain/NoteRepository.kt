package com.baeyer.notesapp.domain

import com.baeyer.notesapp.data.model.Note

interface NoteRepository {
    suspend fun addNote(note: Note)

    suspend fun getNotes(): MutableList<Note>
}