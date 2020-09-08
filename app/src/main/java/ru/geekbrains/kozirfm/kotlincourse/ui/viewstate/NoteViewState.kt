package ru.geekbrains.kozirfm.kotlincourse.ui.viewstate

import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note

class NoteViewState(val note: Note? = null) : BaseViewState<Note?>(note)