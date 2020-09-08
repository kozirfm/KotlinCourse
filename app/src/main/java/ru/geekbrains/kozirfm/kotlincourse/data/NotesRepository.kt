package ru.geekbrains.kozirfm.kotlincourse.data

import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.providers.ArrayDataProvider
import ru.geekbrains.kozirfm.kotlincourse.data.providers.RemoteDataProvider

object NotesRepository {

    private val remoteProvider: RemoteDataProvider = ArrayDataProvider()

    fun getNotes() = remoteProvider.getNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note = note)
    fun changeNote(note: Note, newNote: Note) = remoteProvider.changeNote(note = note, newNote = newNote)
    fun removeNote(note: Note) = remoteProvider.removeNote(note = note)

}
