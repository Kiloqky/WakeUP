package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities

import com.google.gson.annotations.SerializedName

class Hourly {
    @SerializedName("wind_speed")
    var wind: Float = 0.0F

    @SerializedName("dt")
    var dt: Long = 0

    @SerializedName("temp")
    var temp: Float = 0.0F

    @SerializedName("feels_like")
    var feelsLike: Float = 0.0F

    @SerializedName("pressure")
    var pressure: Int = 0

    @SerializedName("humidity")
    var humidity: Int = 0

    @SerializedName("weather")
    lateinit var weather: ArrayList<Weather>
}