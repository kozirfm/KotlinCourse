package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.data.errors.NoAuthException
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.SplashViewState

class SplashViewModel : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        NotesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}
