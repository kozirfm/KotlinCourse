package ru.geekbrains.kozirfm.kotlincourse.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.geekbrains.kozirfm.kotlincourse.R
import ru.geekbrains.kozirfm.kotlincourse.ui.adapters.NotesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        recyclerViewMain.layoutManager = GridLayoutManager(applicationContext, 2)
        notesAdapter = NotesAdapter()
        recyclerViewMain.adapter = notesAdapter

        viewModel.viewState().observe(this, { state ->
            state?.let { notesAdapter.notes = it.notes }
        })

    }
}
