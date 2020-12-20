package ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi.entities.GeocodingMain

interface IGeocodingAPI {
    @GET("maps/api/geocode/json")
    fun loadLocation(
        @Query("latlng") latlng: String,
        @Query("key") key: String
    ): Call<GeocodingMain>
}