package ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities

import com.google.gson.annotations.SerializedName

class Weather {
    class Weather {
        @SerializedName("description")
        lateinit var description: String

        @SerializedName("icon")
        lateinit var icon: String
    }
}