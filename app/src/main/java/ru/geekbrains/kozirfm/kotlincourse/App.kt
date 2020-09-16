package ru.geekbrains.kozirfm.kotlincourse

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.geekbrains.kozirfm.kotlincourse.di.appModule
import ru.geekbrains.kozirfm.kotlincourse.di.mainModule
import ru.geekbrains.kozirfm.kotlincourse.di.noteModule
import ru.geekbrains.kozirfm.kotlincourse.di.splashModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(context = this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}