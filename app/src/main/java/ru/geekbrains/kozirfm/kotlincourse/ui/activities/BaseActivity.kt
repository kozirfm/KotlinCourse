package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.BaseViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.BaseViewState

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        viewModel.viewState().observe(this, Observer { state ->
            state ?: return@Observer
            renderData(state.data)
        })
    }

    abstract fun renderData(data: T)
}