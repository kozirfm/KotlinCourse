package ru.geekbrains.kozirfm.kotlincourse.data

import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.providers.RemoteDataProvider

class NotesRepository(val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.getNotes()
    suspend fun saveNote(note: Note) = remoteProvider.saveNote(note = note)
    suspend fun changeNote(note: Note, newNote: Note) = remoteProvider.changeNote(note = note, newNote = newNote)
    suspend fun removeNote(note: Note) = remoteProvider.removeNote(note = note)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()


}
