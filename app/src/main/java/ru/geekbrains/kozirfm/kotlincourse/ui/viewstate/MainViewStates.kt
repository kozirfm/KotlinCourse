package ru.geekbrains.kozirfm.kotlincourse.ui.viewstate

import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note

sealed class MainViewStates(notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error){
    class ShowNotes(notes: List<Note>?) : MainViewStates(notes = notes)
    class ShowError(error: Throwable?) : MainViewStates(error = error)
}
