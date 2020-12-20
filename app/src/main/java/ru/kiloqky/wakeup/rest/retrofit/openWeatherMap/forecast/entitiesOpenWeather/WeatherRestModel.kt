package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast.entitiesOpenWeather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeatherRestModel : Serializable {
    @SerializedName("id")
    var id = 0

    @SerializedName("main")
    lateinit var main: String

    @SerializedName("description")
    lateinit var description: String

    @SerializedName("icon")
    lateinit var icon: String
}