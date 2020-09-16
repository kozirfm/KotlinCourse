package ru.geekbrains.kozirfm.kotlincourse.data

import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.providers.RemoteDataProvider

class NotesRepository(val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.getNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note = note)
    fun changeNote(note: Note, newNote: Note) = remoteProvider.changeNote(note = note, newNote = newNote)
    fun removeNote(note: Note) = remoteProvider.removeNote(note = note)
    fun getCurrentUser() = remoteProvider.getCurrentUser()


}
