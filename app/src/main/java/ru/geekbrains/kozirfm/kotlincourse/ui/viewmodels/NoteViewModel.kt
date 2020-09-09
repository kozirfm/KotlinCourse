package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.model.NoteResult
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.NoteViewState
import java.util.*

class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {

    fun addOrChangeNote(note: Note? = null, title: String, text: String) {
        if (note == null) {
            if (title != "" || text != "") {
                addObserver(NotesRepository.saveNote(Note(title, text)))
            }
        } else {
            if (note.title != title || note.text != text) {
                val newNote = note.copy(title = title, text = text, lastChanged = Date())
                addObserver(NotesRepository.changeNote(note, newNote))
            }
        }
    }

    fun removeNote(note: Note) {
        addObserver(NotesRepository.removeNote(note))
    }

    private fun addObserver(observed: LiveData<NoteResult>) {
        val observer = Observer<NoteResult> { result ->
            when (result) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = NoteViewState(note = result.data as Note?)
                }
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
        observed.observeForever(observer)
    }

}
