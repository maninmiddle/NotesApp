package com.baeyer.notesapp.presentation.openNote

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.baeyer.notesapp.R
import com.baeyer.notesapp.databinding.ActivityOpenNoteBinding
import com.baeyer.notesapp.presentation.fragments.OpenNoteFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.RuntimeException

@AndroidEntryPoint
class OpenNoteActivity : AppCompatActivity(), OpenNoteFragment.OnFragmentInteractionListener {
    private var screenMode = MODE_UNKNOWN
    private var noteId = -1
    private lateinit var binding: ActivityOpenNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseParams()

        launchRightMode()


    }

    private fun parseParams() {
        if (!intent.hasExtra(SCREEN_MODE)) {
            throw RuntimeException("screen mode was not defined")
        } else {
            val mode = intent.getStringExtra(SCREEN_MODE)

            if (mode != MODE_EDIT && mode != MODE_ADD) {
                throw RuntimeException("Unknown screen mode")
            }
            screenMode = mode
            if (screenMode == MODE_EDIT) {
                if (!intent.hasExtra(NOTE_ITEM_ID)) {
                    throw RuntimeException("Note id is absent")
                }
                noteId = intent.getIntExtra(NOTE_ITEM_ID, 0)
            }
        }
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> OpenNoteFragment.newInstanceEditItem(noteId)
            MODE_ADD -> OpenNoteFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    companion object {
        private const val NOTE_ITEM_ID = "note_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, OpenNoteActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, noteId: Int): Intent {
            val intent = Intent(context, OpenNoteActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_EDIT)
            intent.putExtra(NOTE_ITEM_ID, noteId)
            return intent
        }
    }

    override fun onFragmentInteraction() {
        finish()
    }
}