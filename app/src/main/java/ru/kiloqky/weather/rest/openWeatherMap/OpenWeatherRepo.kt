package ru.kiloqky.weather.rest.openWeatherMap

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherRepo private constructor() {
    object Singleton {
        val api: IOpenWeather = createAdapter()
        private fun createAdapter(): IOpenWeather {
            val adapter = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return adapter.create(IOpenWeather::class.java)
        }
    }
}