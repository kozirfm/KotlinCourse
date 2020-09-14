package ru.geekbrains.kozirfm.kotlincourse.ui.viewstate

import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)