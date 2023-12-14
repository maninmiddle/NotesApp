package com.baeyer.notesapp.presentation.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.ListAdapter
import com.baeyer.notesapp.R
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.databinding.DialogLayoutBinding
import com.baeyer.notesapp.databinding.NoteItemBinding
import com.baeyer.notesapp.presentation.main.MainActivity

class NoteAdapter(
    private val context: Context
) : ListAdapter<Note, NoteViewHolder>(
    NoteItemDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(view)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)

        holder.binding.text.text = note.text

        holder.binding.root.setOnClickListener {
            showEditDialog(position)
        }
    }

    private fun showEditDialog(position: Int) {
        val context = (context as MainActivity)
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogBind = DialogLayoutBinding.inflate(inflater)
        dialog.setContentView(dialogBind.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBind.addNoteBtn.text = context.getString(R.string.stringEditNote)

        dialogBind.addNoteBtn.setOnClickListener {
            context.editNote(
                Note(
                    id = getItem(position).id,
                    name = dialogBind.etName.text.toString(),
                    text = dialogBind.etText.text.toString()
                )
            )
            dialog.cancel()
        }

        dialog.show()
    }


}