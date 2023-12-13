package com.baeyer.notesapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.data.repository.NoteRepositoryImpl
import com.baeyer.notesapp.presentation.adapters.NoteAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryImpl: NoteRepositoryImpl
) : ViewModel() {
    private val _notes = MutableStateFlow<MutableList<Note>>(
        mutableListOf(Note(null, null, null))
    )
    val notes: StateFlow<MutableList<Note>>
        get() = _notes

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addNote(
                note
            )
            _notes.value = repositoryImpl.getNotes()
        }

    }

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _notes.value = repositoryImpl.getNotes()
        }
    }

}

