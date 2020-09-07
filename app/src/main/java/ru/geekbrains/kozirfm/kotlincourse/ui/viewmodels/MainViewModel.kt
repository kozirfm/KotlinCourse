package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.MainViewState

class MainViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        NotesRepository.notesLiveData.observeForever {
            viewStateLiveData.value = MainViewState(notes = it)
        }
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}
