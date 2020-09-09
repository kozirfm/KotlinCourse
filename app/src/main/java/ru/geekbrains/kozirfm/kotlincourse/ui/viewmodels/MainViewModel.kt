package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.model.NoteResult
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.MainViewStates

class MainViewModel : BaseViewModel<List<Note>?, MainViewStates>() {

    private val notesObserver = Observer<NoteResult> { result ->
        result ?: return@Observer
        when (result) {
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = MainViewStates.ShowNotes(notes = result.data as List<Note>?)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = MainViewStates.ShowError(error = result.error)
            }
        }
    }

    private val repositoryNotes: LiveData<NoteResult> = NotesRepository.getNotes()

    init {
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
    }
}
