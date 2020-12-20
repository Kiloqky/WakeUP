package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities

import com.google.gson.annotations.SerializedName


class Main {
    @SerializedName("current")
    lateinit var current: Current

    @SerializedName("hourly")
    lateinit var hourly: ArrayList<Hourly>

    @SerializedName("daily")
    lateinit var daily: ArrayList<Daily>

    class Daily {
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

    class Hourly {
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
}