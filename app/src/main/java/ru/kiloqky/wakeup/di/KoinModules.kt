package ru.kiloqky.wakeup.di

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kiloqky.wakeup.rest.room.KeepDataBase
import ru.kiloqky.wakeup.viewmodels.KeepViewModel

val appModule = module {
    single { Room.databaseBuilder(get(), KeepDataBase::class.java, "keeps").build() }

}
val keepModule = module {
    single { KeepViewModel(get()) }
}