package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_note.*
import ru.geekbrains.kozirfm.kotlincourse.R
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.NoteViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.NoteViewState

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

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

    private var note: Note? = null

    override val viewModel: NoteViewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    override val layoutRes: Int = R.layout.activity_note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        note = intent.getParcelableExtra(EXTRA_NOTE)

        initView(note)

        noteFabSave.setOnClickListener {
            txtFromViewToNote(note)
            onBackPressed()
        }
    }

    private fun initView(note: Note?) {
        note?.let {
            editTxtTitleNoteActivity.setText(it.title)
            editTxtTextNoteActivity.setText(it.text)
        }
    }

    private fun txtFromViewToNote(note: Note?) {
        viewModel.addOrChangeNote(note = note,
                title = editTxtTitleNoteActivity.text.toString(),
                text = editTxtTextNoteActivity.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.noteMenuItemBack -> onBackPressed()
            R.id.noteMenuItemDelete -> {
                note?.let {
                    viewModel.removeNote(it)
                    onBackPressed()
                }
            }
        }
        return true
    }

    override fun renderData(data: Note?) {
        data?.let { println(it) }
    }
}