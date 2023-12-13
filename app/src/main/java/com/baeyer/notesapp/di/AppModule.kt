package com.baeyer.notesapp.di

import android.app.Application
import android.content.Context
import android.service.autofill.UserData
import androidx.room.Room
import com.baeyer.notesapp.data.db.AppDatabase
import com.baeyer.notesapp.data.db.dao.NoteDao
import com.baeyer.notesapp.data.repository.NoteRepositoryImpl
import com.baeyer.notesapp.domain.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }


    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "note_base"
        ).build()
    }

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }


}