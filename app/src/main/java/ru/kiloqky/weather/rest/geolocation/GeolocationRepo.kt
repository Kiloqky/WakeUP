package ru.kiloqky.weather.rest.geolocation

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GeolocationRepo {
    object Singleton {
        var api: IGeolocationAPI = createAdapter()
        private fun createAdapter(): IGeolocationAPI {
            val adapter = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return adapter.create(IGeolocationAPI::class.java)
        }
    }
}