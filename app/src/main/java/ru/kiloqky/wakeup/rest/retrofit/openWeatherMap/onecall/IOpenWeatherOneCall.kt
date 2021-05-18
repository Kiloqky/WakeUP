package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities.WeatherMain

interface IOpenWeatherOneCall {
    @GET("data/2.5/onecall")
    fun loadWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("lang") lang: String,
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") keyAPI: String
    ): Call<WeatherMain>
}