package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_note.*
import ru.geekbrains.kozirfm.kotlincourse.R
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.NoteViewModel

class NoteActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"

        fun start(context: Context, note: Note? = null) {
            Intent(context, NoteActivity::class.java)
                    .run {
                        note?.let {
                            this.putExtra(EXTRA_NOTE, note)
                        }
                        context.startActivity(this)
                    }
        }

    }

    private lateinit var noteViewModel: NoteViewModel
    private var noteId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        initView()

        noteFabSave.setOnClickListener {
            txtFromViewToNote()
            onBackPressed()
        }
    }

    private fun initView() {
        val note: Note? = intent.getParcelableExtra(EXTRA_NOTE)

        note?.let {
            editTxtTitleNoteActivity.setText(it.title)
            editTxtTextNoteActivity.setText(it.text)
            noteId = it.id
        }
    }

    private fun txtFromViewToNote() {
        val id: Int? = noteId
        if (id != null) {
            noteViewModel.addOrChangeNote(
                    id = id,
                    title = editTxtTitleNoteActivity.text.toString(),
                    text = editTxtTextNoteActivity.text.toString())
        } else {
            noteViewModel.addOrChangeNote(
                    title = editTxtTitleNoteActivity.text.toString(),
                    text = editTxtTextNoteActivity.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.noteMenuItemDelete -> onBackPressed()
        }
        return true
    }

}