package ru.kiloqky.wakeup

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kiloqky.wakeup.di.appModule
import ru.kiloqky.wakeup.di.keepModule
import ru.kiloqky.wakeup.di.newsModule
import ru.kiloqky.wakeup.di.weatherModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, keepModule, newsModule, weatherModule))
        }
    }
}