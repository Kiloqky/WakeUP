package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities

import com.google.gson.annotations.SerializedName


class WeatherMain {
    @SerializedName("current")
    lateinit var current: Current

    @SerializedName("hourly")
    lateinit var hourly: MutableList<Hourly>

    @SerializedName("daily")
    lateinit var daily: MutableList<Daily>
}