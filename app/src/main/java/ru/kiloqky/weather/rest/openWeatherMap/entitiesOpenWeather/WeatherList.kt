package ru.kiloqky.weather.rest.openWeatherMap.entitiesOpenWeather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeatherList : Serializable {
    @SerializedName("main")
    lateinit var mainRestModel: MainRestModel

    @SerializedName("weather")
    lateinit var weatherRestModel: Array<WeatherRestModel>

    @SerializedName("wind")
    lateinit var windRestModel: WindRestModel

    @SerializedName("dt")
    var dt: Long =0

    @SerializedName("clouds")
    lateinit var cloudsRestModel: CloudsRestModel
}