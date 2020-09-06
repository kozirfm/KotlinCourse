package ru.geekbrains.kozirfm.kotlincourse.data

import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note

object NotesRepository {

    val notes: ArrayList<Note> = arrayListOf(
            Note(title = "Первая заметка",
                    text = "Текст первой заметки. Не очень длинный, но интересный"),
            Note(title = "Вторая заметка",
                    text = "Текст второй заметки. Не очень длинный, но интересный"),
            Note(title = "Третья заметка",
                    text = "Текст третьей заметки. Не очень длинный, но интересный"),
            Note(title = "Четвертая заметка",
                    text = "Текст четвертой заметки. Не очень длинный, но интересный"),
            Note(title = "Пятая заметка",
                    text = "Текст пятой заметки. Не очень длинный, но интересный"),
            Note(title = "Шестая заметка",
                    text = "Текст шестой заметки. Не очень длинный, но интересный"),
    )
}
