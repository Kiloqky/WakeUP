package ru.kiloqky.wakeup.di

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi.GeocodingRepo
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geolocationApi.GeolocationRepo
import ru.kiloqky.wakeup.rest.retrofit.news.NewsAPIRepo
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.OpenWeatherRepoOneCall
import ru.kiloqky.wakeup.rest.room.keep.KeepDataBase
import ru.kiloqky.wakeup.rest.room.news.NewsDataBase
import ru.kiloqky.wakeup.viewmodels.KeepViewModel
import ru.kiloqky.wakeup.viewmodels.NewsViewModel
import ru.kiloqky.wakeup.viewmodels.WeatherViewModel

val appModule = module {
    single { Room.databaseBuilder(get(), KeepDataBase::class.java, "keeps").build() }
    single { GeolocationRepo() }
    single { GeocodingRepo() }
    single { NewsAPIRepo() }
    single { OpenWeatherRepoOneCall() }
    single { Room.databaseBuilder(get(), NewsDataBase::class.java, "news").build() }
}

val keepModule = module {
    single { KeepViewModel(get()) }
}

val newsModule = module {
    single { NewsViewModel(get(), get(), get()) }
}

val weatherModule = module {
    viewModel {
        WeatherViewModel(get(), get(), get(), get())
    }
}