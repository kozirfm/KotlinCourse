package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import kotlinx.coroutines.launch
import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import java.util.*

class NoteViewModel(private val notesRepository: NotesRepository) : BaseViewModel<Note?>() {

    fun addOrChangeNote(note: Note? = null, title: String, text: String) {
        note?.let {
            if (note.title != title || note.text != text) {
                val newNote = note.copy(title = title, text = text, lastChanged = Date())
                launch {
                    notesRepository.changeNote(note, newNote)
                }
            }
        }
                ?: if (title != "" || text != "") {
                    launch {
                        notesRepository.saveNote(Note(title, text))
                    }
                }
    }

    fun removeNote(note: Note) {
        launch {
            notesRepository.removeNote(note)
        }
    }
}
