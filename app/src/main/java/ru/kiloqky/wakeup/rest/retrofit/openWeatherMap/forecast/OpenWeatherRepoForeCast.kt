package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherRepoForeCast private constructor() {
    object Singleton {
        val api: IOpenWeatherForeCast = createAdapter()
        private fun createAdapter(): IOpenWeatherForeCast {
            val adapter = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return adapter.create(IOpenWeatherForeCast::class.java)
        }
    }
}