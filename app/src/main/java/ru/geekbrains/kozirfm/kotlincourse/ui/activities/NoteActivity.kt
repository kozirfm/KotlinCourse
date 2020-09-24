package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_note.*
import org.koin.android.ext.android.inject
import ru.geekbrains.kozirfm.kotlincourse.R
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.ui.fragments.DeleteNoteDialog
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.NoteViewModel

class NoteActivity : BaseActivity<Note?>() {

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

    override val model: NoteViewModel by inject()

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
        model.addOrChangeNote(note = note,
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
                note?.let { note ->
                    supportFragmentManager.findFragmentByTag(DeleteNoteDialog.TAG)
                    DeleteNoteDialog.createInstance {
                        onDeleteNote(note)
                    }.show(supportFragmentManager, DeleteNoteDialog.TAG)
                }
            }
        }
        return true
    }

    override fun renderData(data: Note?) {
        data?.let {
            Toast.makeText(applicationContext, getString(R.string.complete), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onDeleteNote(note: Note) {
        model.removeNote(note)
        onBackPressed()
    }

}
