package ru.kiloqky.weather.rest.openWeatherMap.entitiesOpenWeather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CityRestModel : Serializable {
    @SerializedName("name")
    lateinit var name: String

    @SerializedName("country")
    lateinit var country: String

    @SerializedName("sunrise")
    var sunrise: Long = 0

    @SerializedName("sunset")
    var sunset: Long = 0
}