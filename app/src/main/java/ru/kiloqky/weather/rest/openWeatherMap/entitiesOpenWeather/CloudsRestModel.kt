package ru.kiloqky.weather.rest.openWeatherMap.entitiesOpenWeather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CloudsRestModel : Serializable {
    @SerializedName("all")
    var all = 0
}