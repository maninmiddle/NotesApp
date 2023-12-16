package com.baeyer.notesapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.domain.AddNoteUseCase
import com.baeyer.notesapp.domain.DeleteNoteUseCase
import com.baeyer.notesapp.domain.EditNoteUseCase
import com.baeyer.notesapp.domain.GetNoteByIdUseCase
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
    private val editNoteUseCase: EditNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ViewModel() {

    private val _note = MutableStateFlow(Note(null, null, null))
    val note: StateFlow<Note>
        get() = _note

    private val _notes = MutableStateFlow(
        mutableListOf(Note(null, null, null))
    )
    val notes: StateFlow<MutableList<Note>>
        get() = _notes

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            addNoteUseCase.addNote(note)
            getNotes()
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase.deleteNote(note)
            getNotes()
        }
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _note.value = getNoteByIdUseCase.getNoteById(id)
        }
    }

    fun editNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            editNoteUseCase.editNote(note)
            getNotes()
        }
    }

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _notes.value = getNotesUseCase.getNotes()
        }
    }


}

