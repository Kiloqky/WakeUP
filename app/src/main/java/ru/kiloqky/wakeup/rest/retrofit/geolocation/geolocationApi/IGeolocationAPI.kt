package ru.kiloqky.wakeup.rest.retrofit.geolocation.geolocationApi

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geolocationApi.entities.Geolocation

interface IGeolocationAPI {
    @POST("geolocation/v1/geolocate")
    fun loadLocation(@Query("key") key: String?): Call<Geolocation>
}