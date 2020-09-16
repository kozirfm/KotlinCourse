package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import org.koin.android.ext.android.inject
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.SplashViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.SplashViewState

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel: SplashViewModel by inject()

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
