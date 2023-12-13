package com.baeyer.notesapp.presentation.adapters


import androidx.recyclerview.widget.DiffUtil
import com.baeyer.notesapp.data.model.Note

class NoteItemDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}