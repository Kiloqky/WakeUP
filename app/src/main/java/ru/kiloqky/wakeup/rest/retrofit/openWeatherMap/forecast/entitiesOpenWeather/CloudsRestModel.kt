package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast.entitiesOpenWeather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CloudsRestModel : Serializable {
    @SerializedName("all")
    var all = 0
}