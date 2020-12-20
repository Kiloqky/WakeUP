package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherReoOneCall {
    object Singleton{
        val api: IOpenWeatherOneCall = createAdapter()
        private fun createAdapter(): IOpenWeatherOneCall {
            val adapter = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return adapter.create(IOpenWeatherOneCall::class.java)
        }
    }
}