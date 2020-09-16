package ru.geekbrains.kozirfm.kotlincourse.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module.module
import ru.geekbrains.kozirfm.kotlincourse.data.NotesRepository
import ru.geekbrains.kozirfm.kotlincourse.data.providers.FirestoreDataProvider
import ru.geekbrains.kozirfm.kotlincourse.data.providers.RemoteDataProvider
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.MainViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.NoteViewModel
import ru.geekbrains.kozirfm.kotlincourse.ui.viewmodels.SplashViewModel

val appModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }
    single<RemoteDataProvider> { FirestoreDataProvider(get(), get()) }
    single { NotesRepository(get()) }
}

val splashModule = module {
    factory { SplashViewModel(get()) }
}

val mainModule = module {
    factory { MainViewModel(get()) }
}

val noteModule = module {
    factory { NoteViewModel(get()) }
}