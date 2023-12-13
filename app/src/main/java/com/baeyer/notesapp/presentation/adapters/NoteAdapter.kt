package com.baeyer.notesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.databinding.NoteItemBinding

class NoteAdapter : ListAdapter<Note, NoteViewHolder>(
    NoteItemDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(view)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)

        holder.binding.text.text = note.text
    }


}