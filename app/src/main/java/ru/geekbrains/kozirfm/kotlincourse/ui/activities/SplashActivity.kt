package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.SplashViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.SplashViewState

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }

}
