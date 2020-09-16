package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import ru.geekbrains.kozirfm.kotlincourse.R
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.ui.adapters.NotesAdapter
import ru.geekbrains.kozirfm.kotlincourse.ui.fragments.LogoutDialog
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.MainViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.MainViewStates

class MainActivity : BaseActivity<List<Note>?, MainViewStates>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val notesAdapter: NotesAdapter by lazy {
        NotesAdapter {
            NoteActivity.start(this, it)
        }
    }

    override val viewModel: MainViewModel by inject()

    override val layoutRes: Int = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerViewMain.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerViewMain.adapter = notesAdapter

        mainFabAdd.setOnClickListener {
            NoteActivity.start(this)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mainMenuItemLogout -> showLogoutDialog().let { true }
            else -> false
        }

    }

    override fun renderData(data: List<Note>?) {
        data?.let { notesAdapter.notes = it }
    }

    private fun showLogoutDialog() {
        LogoutDialog.createInstance { onLogout() }.show(supportFragmentManager, LogoutDialog.TAG)
    }

    private fun onLogout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }

    }
}
