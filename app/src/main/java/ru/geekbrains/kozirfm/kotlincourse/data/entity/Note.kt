package ru.geekbrains.kozirfm.kotlincourse.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(val title: String = "", val text: String = "", val lastChanged: Date = Date()) : Parcelable
