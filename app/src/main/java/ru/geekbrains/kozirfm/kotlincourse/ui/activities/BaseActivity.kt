package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.BaseViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.BaseViewState

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        viewModel.viewState().observeForever { state ->
            state ?: return@observeForever
            renderData(state.data)
            showError(state.error)
        }
    }

    abstract fun renderData(data: T)

    private fun showError(error: Throwable?){
        error?.let {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }

}
