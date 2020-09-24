package ru.geekbrains.kozirfm.kotlincourse.data.providers

import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note
import ru.geekbrains.kozirfm.kotlincourse.data.entity.User
import ru.geekbrains.kozirfm.kotlincourse.data.model.NoteResult

interface RemoteDataProvider {
    fun getNotes(): ReceiveChannel<NoteResult>
    suspend fun saveNote(note: Note): Note
    suspend fun changeNote(note: Note, newNote: Note): Note
    suspend fun removeNote(note: Note): Note
    suspend fun getCurrentUser(): User?
}