package com.baeyer.notesapp.data.repository

import com.baeyer.notesapp.data.db.dao.NoteDao
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.domain.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    override suspend fun getNotes(): MutableList<Note> {
        return noteDao.getNotes()
    }

    override suspend fun editNote(note: Note) {
        addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }
}