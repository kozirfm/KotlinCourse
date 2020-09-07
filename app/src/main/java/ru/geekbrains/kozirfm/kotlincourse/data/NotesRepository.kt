package ru.geekbrains.kozirfm.kotlincourse.data

import androidx.lifecycle.MutableLiveData
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note

object NotesRepository {

    val notesLiveData: MutableLiveData<List<Note>> = MutableLiveData()

    val notes: ArrayList<Note> = arrayListOf(
            Note(id = 0, title = "Первая заметка",
                    text = "Текст первой заметки. Не очень длинный, но интересный"),
            Note(id = 1, title = "Вторая заметка",
                    text = "Текст второй заметки. Не очень длинный, но интересный"),
            Note(id = 2, title = "Третья заметка",
                    text = "Текст третьей заметки. Не очень длинный, но интересный"),
            Note(id = 3, title = "Четвертая заметка",
                    text = "Текст четвертой заметки. Не очень длинный, но интересный"),
            Note(id = 4, title = "Пятая заметка",
                    text = "Текст пятой заметки. Не очень длинный, но интересный"),
            Note(id = 5, title = "Шестая заметка",
                    text = "Текст шестой заметки. Не очень длинный, но интересный"),
            Note(id = 6, title = "Седьмая заметка",
                    text = "Текст седьмой заметки. Не очень длинный, но интересный"),
            Note(id = 7, title = "Восьмая заметка",
                    text = "Текст восьмой заметки. Не очень длинный, но интересный"),
    )

    init {
        notesLiveData.value = notes
    }

    fun saveNote(note: Note) {
        notes.add(note)
        notesLiveData.value = notes
    }

    fun changeNote(note: Note, newNote: Note) {
        notes.remove(note)
        notes.add(newNote)
        notesLiveData.value = notes
    }

    fun removeNote(note: Note) {
        notes.remove(note)
        notesLiveData.value = notes
    }

}
