package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast.entitiesOpenWeather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MainRestModel : Serializable {
    @SerializedName("temp")
    var temp = 0f

    @SerializedName("feels_like")
    var feelsLike = 0f

    @SerializedName("pressure")
    var pressure = 0f

    @SerializedName("humidity")
    var humidity = 0f

    @SerializedName("temp_min")
    var tempMin = 0f

    @SerializedName("temp_max")
    var tempMax = 0f
}