package ru.kiloqky.weather.rest.openWeatherMap.entitiesOpenWeather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeatherLists : Serializable {
    @SerializedName("list")
    lateinit var weatherLists: Array<WeatherList>

    @SerializedName("city")
    lateinit var cityRestModel: CityRestModel
}