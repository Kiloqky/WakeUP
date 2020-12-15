package ru.kiloqky.weather.rest.openWeatherMap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kiloqky.weather.rest.openWeatherMap.entitiesOpenWeather.WeatherLists

interface IOpenWeather {
    @GET("data/2.5/forecast")
    fun loadWeather(
        @Query("q") city: String?,
        @Query("appid") keyAPI: String?
    ): Call<WeatherLists>

    @GET("data/2.5/forecast")
    fun loadWeather(
        @Query("lat") lat: Float,
        @Query("lon") lng: Float,
        @Query("appid") keyAPI: String?
    ): Call<WeatherLists?>?
}