package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import androidx.lifecycle.ViewModel
import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note

class NoteViewModel : ViewModel() {

    fun addOrChangeNote(id: Int = NotesRepository.notes.size, title: String, text: String) {
        if (id == NotesRepository.notes.size) {
            if (title != "" || text != "") {
                NotesRepository.saveNote(Note(id, title, text))
            }
        } else {
            NotesRepository.changeNote(id = id, Note(id, title, text))
        }
    }
}
