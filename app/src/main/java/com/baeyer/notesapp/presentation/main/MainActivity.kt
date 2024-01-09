package com.baeyer.notesapp.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.databinding.ActivityMainBinding
import com.baeyer.notesapp.presentation.adapters.NoteAdapter
import com.baeyer.notesapp.presentation.openNote.OpenNoteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var noteAdapter: NoteAdapter
    private var updated = false


    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNoteBtn.setOnClickListener {
            val intent = OpenNoteActivity.newIntentAddItem(this)
            startActivity(intent)
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
        if (notes.isEmpty()) {
            binding.addYourFirstNote.visibility = View.VISIBLE
            binding.rvLayout.visibility = View.GONE
        } else {
            binding.rvLayout.visibility = View.VISIBLE
            binding.addYourFirstNote.visibility = View.GONE
            if (!updated) {
                updated = true
                noteAdapter = NoteAdapter()
                noteAdapter.submitList(notes)
                setupOnClickListener()
                binding.rvLayout.adapter = noteAdapter
                binding.rvLayout.layoutManager = GridLayoutManager(this@MainActivity, 2)
                setupSwipeListener(binding.rvLayout)
            } else {
                noteAdapter.submitList(notes)
            }
        }
    }

    private fun setupOnClickListener() {
        noteAdapter.onShopItemClickListener = { note ->
            val intent = OpenNoteActivity.newIntentEditItem(this, note.id!!)
            startActivity(intent)
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
}