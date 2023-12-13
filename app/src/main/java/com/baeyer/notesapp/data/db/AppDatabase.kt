package com.baeyer.notesapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.baeyer.notesapp.data.db.dao.NoteDao
import com.baeyer.notesapp.data.model.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}