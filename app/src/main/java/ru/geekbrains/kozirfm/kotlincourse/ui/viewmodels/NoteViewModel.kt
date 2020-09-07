package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import androidx.lifecycle.ViewModel
import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note

class NoteViewModel : ViewModel() {

    fun addOrChangeNote(note: Note? = null, id: Int = NotesRepository.notes.size, title: String, text: String) {
        if (note == null) {
            if (title != "" || text != "") {
                NotesRepository.saveNote(Note(id, title, text))
            }
        } else {
            if (note.title != title || note.text != text){
                val newNote = note.copy(title = title, text = text)
                NotesRepository.changeNote(note, newNote)
            }
        }
    }
}
