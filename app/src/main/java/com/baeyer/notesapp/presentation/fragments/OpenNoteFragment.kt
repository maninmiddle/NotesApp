package com.baeyer.notesapp.presentation.fragments

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.CharacterStyle
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.text.getSpans
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.baeyer.notesapp.R
import com.baeyer.notesapp.data.model.Note
import com.baeyer.notesapp.databinding.FragmentOpenNoteBinding
import com.baeyer.notesapp.domain.model.FontStyle
import com.baeyer.notesapp.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OpenNoteFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentOpenNoteBinding? = null
    private val binding: FragmentOpenNoteBinding
        get() = _binding ?: throw RuntimeException("FragmentOpenNoteBinding == null")
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
        _binding = FragmentOpenNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        launchRightMode()
        binding.editTextNoteText.customSelectionActionModeCallback = CustomActionModeCallback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchModeEdit()
            MODE_ADD -> launchModeAdd()
        }
    }

    private fun launchModeAdd() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.editTextNoteText.text.isNotEmpty() || binding.editTextNoteTitle.text.isNotEmpty()) {
                    addNote(
                        Note(
                            id = null,
                            name = binding.editTextNoteTitle.text.toString(),
                            text = binding.editTextNoteText.text.toString(),
                            lastModified = System.currentTimeMillis()
                        )
                    )
                }
                closeListener?.onFragmentInteraction()
            }
        })

        binding.icBackArrow.setOnClickListener {
            if (binding.editTextNoteText.text.isNotEmpty() || binding.editTextNoteTitle.text.isNotEmpty()) {
                addNote(
                    Note(
                        id = null,
                        name = binding.editTextNoteTitle.text.toString(),
                        text = binding.editTextNoteText.text.toString(),
                        lastModified = System.currentTimeMillis()
                    )
                )
            }
            closeListener?.onFragmentInteraction()
        }
    }

    private fun launchModeEdit() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                editNote(
                    Note(
                        id = noteItemId,
                        name = binding.editTextNoteTitle.text.toString(),
                        text = binding.editTextNoteText.text.toString(),
                        lastModified = System.currentTimeMillis()
                    )
                )
                closeListener?.onFragmentInteraction()
            }
        })
        viewModel.getNoteById(noteItemId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.note.collect { note ->
                    binding.editTextNoteText.setText(note.text.toString())
                    binding.editTextNoteTitle.setText(note.name.toString())
                }
            }
        }
        startAutoSave()
        binding.icBackArrow.setOnClickListener {
            editNote(
                Note(
                    id = noteItemId,
                    name = binding.editTextNoteTitle.text.toString(),
                    text = binding.editTextNoteText.text.toString(),
                    lastModified = System.currentTimeMillis()
                )
            )
            closeListener?.onFragmentInteraction()
        }
    }

    private fun startAutoSave() {
        binding.editTextNoteText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // not used
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length % 5 == 0) {
                    editNote(
                        Note(
                            id = noteItemId,
                            name = binding.editTextNoteTitle.text.toString(),
                            text = binding.editTextNoteText.text.toString(),
                            lastModified = System.currentTimeMillis()
                        )
                    )
                }
            }
        })
    }

    private inner class CustomActionModeCallback : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = MenuInflater(context)
            inflater.inflate(R.menu.menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.menuBold -> {
                    addStyleToSelectedText(FontStyle.BOLD)
                    mode?.finish()
                    true
                }

                R.id.menuItalic -> {
                    addStyleToSelectedText(FontStyle.ITALIC)
                    mode?.finish()
                    true
                }

                R.id.menuUnderline -> {
                    addStyleToSelectedText(FontStyle.UNDERLINE)
                    mode?.finish()
                    true
                }

                R.id.menuMonospace -> {
                    addStyleToSelectedText(FontStyle.MONOSPACE)
                    mode?.finish()
                    true
                }

                R.id.menuClear -> {
                    clearSelectedText()
                    mode?.finish()
                    true
                }


                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
        }
    }

    private fun clearSelectedText() {
        val startText = binding.editTextNoteText.selectionStart
        val endText = binding.editTextNoteText.selectionEnd
        val allText = binding.editTextNoteText.text

        if (startText != endText) {
            val existingStyle =
                allText.getSpans(startText, endText, CharacterStyle::class.java)
            

            for (span in existingStyle) {
                if (span is StyleSpan || span is UnderlineSpan || span is TypefaceSpan) {
                    allText.removeSpan(span)
                }
            }
        }
    }

    private fun addStyleToSelectedText(fontStyle: FontStyle) {
        val startText = binding.editTextNoteText.selectionStart
        val endText = binding.editTextNoteText.selectionEnd
        val allText = binding.editTextNoteText.text
        if (startText != endText) {

            val styleSpan = when (fontStyle) {
                FontStyle.BOLD -> StyleSpan(Typeface.BOLD)
                FontStyle.ITALIC -> StyleSpan(Typeface.ITALIC)
                FontStyle.UNDERLINE -> UnderlineSpan()
                FontStyle.MONOSPACE -> TypefaceSpan("monospace")

            }

            allText.setSpan(
                styleSpan,
                startText,
                endText,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )


        }
    }

    private fun editNote(note: Note) {
        viewModel.editNote(note)
    }

    private fun addNote(note: Note) {
        viewModel.addNote(note)
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
