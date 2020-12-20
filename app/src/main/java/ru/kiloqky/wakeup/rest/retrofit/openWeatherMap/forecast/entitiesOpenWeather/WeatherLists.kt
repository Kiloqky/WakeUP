package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast.entitiesOpenWeather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeatherLists : Serializable {
    @SerializedName("list")
    lateinit var weatherLists: Array<WeatherList>

    @SerializedName("city")
    lateinit var cityRestModel: CityRestModel
}