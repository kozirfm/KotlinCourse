package ru.geekbrains.kozirfm.kotlincourse.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository

class MainViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = MainViewState(notes = NotesRepository.notes)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

}
