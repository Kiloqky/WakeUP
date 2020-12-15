package ru.kiloqky.weather.rest.geolocation.entitiesGeolocation

import com.google.gson.annotations.SerializedName

class LocationCity {
    @SerializedName("lat")
    var lat = 0f

    @SerializedName("lng")
    var lng = 0f
}