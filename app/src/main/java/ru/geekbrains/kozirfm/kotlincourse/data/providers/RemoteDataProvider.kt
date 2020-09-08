package ru.geekbrains.kozirfm.kotlincourse.data.providers

import androidx.lifecycle.LiveData
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.model.NoteResult

interface RemoteDataProvider {
    fun getNotes(): LiveData<NoteResult>
    fun changeNote(note: Note, newNote: Note): LiveData<NoteResult>
    fun removeNote(note: Note): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
}