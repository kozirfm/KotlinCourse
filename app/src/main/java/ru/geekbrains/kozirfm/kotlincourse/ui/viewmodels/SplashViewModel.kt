package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import kotlinx.coroutines.launch
import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.data.errors.NoAuthException

class SplashViewModel(private val notesRepository: NotesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() = launch {
        notesRepository.getCurrentUser()?.let {
            setData(true)
        } ?: setError(NoAuthException())
    }
}
