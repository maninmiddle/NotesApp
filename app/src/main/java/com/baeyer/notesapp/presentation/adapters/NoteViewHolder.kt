package com.baeyer.notesapp.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.databinding.NoteItemBinding

class NoteViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note, onShopItemClickListener: ((Note) -> Unit)?) {
        binding.noteName.text = note.name
        binding.noteText.text = note.text

        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(note)
        }

    }

}
