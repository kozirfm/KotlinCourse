package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.geekbrains.kozirfm.kotlincourse.R
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.ui.adapters.NotesAdapter
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.MainViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.MainViewState

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    private lateinit var notesAdapter: NotesAdapter

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override val layoutRes: Int = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerViewMain.layoutManager = GridLayoutManager(applicationContext, 2)
        notesAdapter = NotesAdapter {
            NoteActivity.start(this, it)
        }
        recyclerViewMain.adapter = notesAdapter

        mainFabAdd.setOnClickListener {
            NoteActivity.start(this)
        }

    }

    override fun renderData(data: List<Note>?) {
        data?.let { notesAdapter.notes = it }
    }

    override fun showError(error: Throwable?) {
        error?.let {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }


}
