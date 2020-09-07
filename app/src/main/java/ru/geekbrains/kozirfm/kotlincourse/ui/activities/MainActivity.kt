package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.geekbrains.kozirfm.kotlincourse.R
import ru.geekbrains.kozirfm.kotlincourse.ui.adapters.NotesAdapter
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recyclerViewMain.layoutManager = GridLayoutManager(applicationContext, 2)
        notesAdapter = NotesAdapter {
            NoteActivity.start(this, it)
        }
        recyclerViewMain.adapter = notesAdapter

        viewModel.viewState().observe(this, { state ->
            state?.let { notesAdapter.notes = it.notes }
        })

        mainFabAdd.setOnClickListener {
            NoteActivity.start(this)
        }

    }
}
