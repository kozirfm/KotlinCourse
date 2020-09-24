package ru.geekbrains.kozirfm.kotlincourse.ui.activities

import org.koin.android.ext.android.inject
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.SplashViewModel

class SplashActivity : BaseActivity<Boolean?>() {

    override val model: SplashViewModel by inject()

    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        model.requestUser()
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
