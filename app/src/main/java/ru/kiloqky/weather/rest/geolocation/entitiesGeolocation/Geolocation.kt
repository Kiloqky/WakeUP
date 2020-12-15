package ru.kiloqky.weather.rest.geolocation.entitiesGeolocation

import com.google.gson.annotations.SerializedName
import ru.kiloqky.weather.rest.geolocation.entitiesGeolocation.LocationCity

class Geolocation {
    @SerializedName("location")
    var location: LocationCity? = null
}