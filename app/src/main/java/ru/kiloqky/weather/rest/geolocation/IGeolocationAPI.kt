package ru.kiloqky.weather.rest.geolocation

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import ru.kiloqky.weather.rest.geolocation.entitiesGeolocation.Geolocation

interface IGeolocationAPI {
    @POST("geolocation/v1/geolocate")
    fun loadLocation(@Query("key") key: String?): Call<Geolocation>
}