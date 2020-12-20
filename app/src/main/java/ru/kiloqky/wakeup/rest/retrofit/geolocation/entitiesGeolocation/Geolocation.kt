package ru.kiloqky.wakeup.rest.retrofit.geolocation.entitiesGeolocation

import com.google.gson.annotations.SerializedName

class Geolocation {
    @SerializedName("location")
    var location: LocationCity? = null
}