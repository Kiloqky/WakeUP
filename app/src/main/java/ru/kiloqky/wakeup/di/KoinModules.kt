package ru.kiloqky.wakeup.di

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi.GeocodingRepo
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geolocationApi.GeolocationRepo
import ru.kiloqky.wakeup.rest.retrofit.news.NewsAPIRepo
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.OpenWeatherRepoOneCall
import ru.kiloqky.wakeup.rest.room.KeepDataBase
import ru.kiloqky.wakeup.viewmodels.KeepViewModel
import ru.kiloqky.wakeup.viewmodels.NewsViewModel
import ru.kiloqky.wakeup.viewmodels.WeatherViewModel

val appModule = module {
    single { Room.databaseBuilder(get(), KeepDataBase::class.java, "keeps").build() }
    single { GeolocationRepo() }
    single { GeocodingRepo() }
    single { NewsAPIRepo() }
    single { OpenWeatherRepoOneCall() }
}
val keepModule = module {
    single { KeepViewModel(get()) }
}

val newsModule = module {
    viewModel { NewsViewModel(get(), get()) }
}
val weatherModule = module {
    viewModel {
        WeatherViewModel(get(), get(), get(), get())
    }
}