package com.baeyer.notesapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.domain.AddNoteUseCase
import com.baeyer.notesapp.domain.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {
    private val _notes = MutableStateFlow(
        mutableListOf(Note(null, null, null))
    )
    val notes: StateFlow<MutableList<Note>>
        get() = _notes

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            addNoteUseCase.addNote(note)
            _notes.value = getNotesUseCase.getNotes()
        }

    }

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _notes.value = getNotesUseCase.getNotes()
        }
    }

}

