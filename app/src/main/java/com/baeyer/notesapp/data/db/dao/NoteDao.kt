package com.baeyer.notesapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.baeyer.notesapp.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): MutableList<Note>

    @Insert
    fun addNote(note: Note)
}