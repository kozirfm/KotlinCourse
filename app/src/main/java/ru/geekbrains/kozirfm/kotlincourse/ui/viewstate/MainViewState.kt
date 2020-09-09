package ru.geekbrains.kozirfm.kotlincourse.ui.viewstate

import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error)
