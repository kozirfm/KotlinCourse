package ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewstate.BaseViewState

open class BaseViewModel<T, S : BaseViewState<T>> : ViewModel() {
    open val viewStateLiveData = MutableLiveData<S>()
    open fun viewState(): LiveData<S> = viewStateLiveData
}