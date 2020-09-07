package ru.geekbrains.kozirfm.kotlincourse.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(var id: Int, var title: String, var text: String) : Parcelable
