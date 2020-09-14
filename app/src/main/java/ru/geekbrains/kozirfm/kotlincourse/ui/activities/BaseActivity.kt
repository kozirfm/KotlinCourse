package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import ru.geekbrains.kozirfm.kotlincourse.R
import ru.geekbrains.kozirfm.kotlincourse.data.errors.NoAuthException
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.BaseViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.BaseViewState

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    companion object{
        const val RC_SIGN_IN = 4242
    }

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let { setContentView(it) }


        viewModel.viewState().observeForever { state ->
            state ?: return@observeForever
            renderData(state.data)
            renderError(state.error)
        }
    }

    abstract fun renderData(data: T)

    private fun renderError(error: Throwable?){
        when(error){
            is NoAuthException -> {startLogin()
            }
            else -> error?.let { showError(it) }
        }
    }

    private fun showError(error: Throwable?){
        error?.let {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startLogin(){
        val providers = listOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.AppTheme)
                .setAvailableProviders(providers)
                .build()

        startActivityForResult(intent, RC_SIGN_IN)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK){
            finish()
        }
    }

}
