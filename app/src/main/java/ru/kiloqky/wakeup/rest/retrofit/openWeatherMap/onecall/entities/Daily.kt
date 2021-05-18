package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities

import com.google.gson.annotations.SerializedName

class Daily {
    @SerializedName("dt")
    var dt: Long = 0

    @SerializedName("temp")
    lateinit var temp: Temp

    class Temp {
        @SerializedName("day")
        val day: Float = 0.0F
    }

    @SerializedName("feels_like")
    lateinit var feelsLike: FeelsLike

    class FeelsLike {
        @SerializedName("day")
        val day: Float = 0.0F
    }

    @SerializedName("pressure")
    var pressure: Int = 0

    @SerializedName("humidity")
    var humidity: Int = 0

    @SerializedName("weather")
    lateinit var weather: ArrayList<Weather>

    @SerializedName("wind_speed")
    var wind: Float = 0.0F
}