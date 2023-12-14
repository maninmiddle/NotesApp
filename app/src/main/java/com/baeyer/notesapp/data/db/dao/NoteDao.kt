package com.baeyer.notesapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.baeyer.notesapp.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): MutableList<Note>

    @Upsert
    fun addNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}