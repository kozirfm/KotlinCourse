package ru.geekbrains.kozirfm.kotlincourse.data.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.model.NoteResult

class ArrayDataProvider : RemoteDataProvider {

    private var notes: ArrayList<Note> = arrayListOf(
            Note(title = "Первая заметка",
                    text = "Текст первой заметки. Не очень длинный, но интересный"),
            Note(title = "Вторая заметка",
                    text = "Текст второй заметки. Не очень длинный, но интересный"),
            Note(title = "Третья заметка",
                    text = "Текст третьей заметки. Не очень длинный, но интересный"),
            Note(title = "Четвертая заметка",
                    text = "Текст четвертой заметки. Не очень длинный, но интересный"),
            Note(title = "Пятая заметка",
                    text = "Текст пятой заметки. Не очень длинный, но интересный"),
            Note(title = "Шестая заметка",
                    text = "Текст шестой заметки. Не очень длинный, но интересный"),
            Note(title = "Седьмая заметка",
                    text = "Текст седьмой заметки. Не очень длинный, но интересный"),
            Note(title = "Восьмая заметка",
                    text = "Текст восьмой заметки. Не очень длинный, но интересный"),
    )

    private val notesLiveData: MutableLiveData<NoteResult> = MutableLiveData()

    init {
        notesLiveData.value = NoteResult.Success(notes)
    }

    override fun getNotes(): LiveData<NoteResult> {
        val result = notesLiveData
        result.value = NoteResult.Success<List<Note>>(notes)
        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = notesLiveData
        notes.add(note)
        result.value = NoteResult.Success(notes)
        return result
    }

    override fun changeNote(note: Note, newNote: Note): LiveData<NoteResult> {
        val result = notesLiveData
        notes.remove(note)
        notes.add(newNote)
        result.value = NoteResult.Success(notes)
        return result
    }

    override fun removeNote(note: Note): LiveData<NoteResult> {
        val result = notesLiveData
        notes.remove(note)
        result.value = NoteResult.Success(notes)
        return result
    }
}
