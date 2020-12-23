package ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GeocodingRepo {
    var api: IGeocodingAPI = createAdapter()
    private fun createAdapter(): IGeocodingAPI {
        val adapter = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return adapter.create(IGeocodingAPI::class.java)

    }
}