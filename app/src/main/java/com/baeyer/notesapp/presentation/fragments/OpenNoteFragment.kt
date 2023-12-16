package com.baeyer.notesapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.baeyer.notesapp.R
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.databinding.FragmentOpenNoteBinding
import com.baeyer.notesapp.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OpenNoteFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentOpenNoteBinding
    private var closeListener: OnFragmentInteractionListener? = null
    private var screenMode = MODE_UNKNOWN
    private var noteItemId = -1


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            closeListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOpenNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        launchRightMode()
        binding.toolbar.setNavigationOnClickListener {
            closeListener?.onFragmentInteraction()
        }


    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchModeEdit()
            MODE_ADD -> launchModeAdd()
        }
    }

    private fun launchModeAdd() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.saveNote -> {
                    viewModel.addNote(
                        Note(
                            null,
                            binding.editTextNoteTitle.text.toString(),
                            binding.editTextNoteText.text.toString()
                        )
                    )
                    closeListener?.onFragmentInteraction()
                    true
                }

                else -> {
                    false
                }
            }
        }

    }

    private fun launchModeEdit() {
        viewModel.getNoteById(noteItemId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.note.collect { note ->
                    binding.editTextNoteText.setText(note.text.toString())
                    binding.editTextNoteTitle.setText(note.name.toString())
                }
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            closeListener?.onFragmentInteraction()
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.saveNote -> {
                    viewModel.editNote(
                        Note(
                            id = noteItemId,
                            name = binding.editTextNoteTitle.text.toString(),
                            text = binding.editTextNoteText.text.toString()
                        )
                    )
                    closeListener?.onFragmentInteraction()
                    true
                }

                else -> false
            }
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Screen Mode absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(NOTE_ITEM_ID)) {
                throw RuntimeException("Note Item Id is absent")
            }
            noteItemId = args.getInt(NOTE_ITEM_ID, 0)
        }
    }

    companion object {
        private const val NOTE_ITEM_ID = "note_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): OpenNoteFragment {
            return OpenNoteFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(noteId: Int): OpenNoteFragment {
            return OpenNoteFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(NOTE_ITEM_ID, noteId)
                }
            }
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction()
    }
}