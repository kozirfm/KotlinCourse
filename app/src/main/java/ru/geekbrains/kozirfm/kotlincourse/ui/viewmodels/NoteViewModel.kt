package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.NoteViewState

class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {

    fun addOrChangeNote(note: Note? = null, title: String, text: String) {
        if (note == null) {
            if (title != "" || text != "") {
                viewStateLiveData.value = NoteViewState(note)
                NotesRepository.saveNote(Note(title, text))
            }
        } else {
            if (note.title != title || note.text != text) {
                val newNote = note.copy(title = title, text = text)
                NotesRepository.changeNote(note, newNote)
            }
        }
    }

    fun removeNote(note: Note) {
        NotesRepository.removeNote(note)
    }
}
