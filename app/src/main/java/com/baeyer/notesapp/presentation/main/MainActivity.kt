package com.baeyer.notesapp.presentation.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.databinding.ActivityMainBinding
import com.baeyer.notesapp.databinding.DialogLayoutBinding
import com.baeyer.notesapp.presentation.adapters.NoteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var noteAdapter: NoteAdapter
    private var updated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNoteBtn.setOnClickListener {
            showAddNoteDialog()
        }

        viewModel.getNotes()


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notes.collect { notes ->
                    rvSetup(notes)
                }
            }
        }
    }

    private fun rvSetup(notes: MutableList<Note>) {
        if (!updated) {
            noteAdapter = NoteAdapter(this@MainActivity)
            noteAdapter.submitList(notes)
            binding.rvLayout.adapter = noteAdapter
            binding.rvLayout.layoutManager = LinearLayoutManager(this@MainActivity)
            setupSwipeListener(binding.rvLayout)
        }

    }

    private fun setupSwipeListener(rvLayout: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentItem = noteAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteNote(currentItem)
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvLayout)
    }

    private fun showAddNoteDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val dialogBinding = DialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.addNoteBtn.setOnClickListener {
            addNote(
                dialogBinding.etName.text.toString(),
                dialogBinding.etText.text.toString()
            )
            dialog.cancel()
        }
        dialog.show()
    }


    private fun addNote(noteName: String, noteText: String) {

        viewModel.addNote(
            Note(
                id = null,
                name = noteName,
                text = noteText
            )
        )
    }

    fun editNote(note: Note) {
        viewModel.editNote(note)
    }
}