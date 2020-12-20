package ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi.entities

import com.google.gson.annotations.SerializedName

class GeocodingMain {
    @SerializedName("results")
    lateinit var results: ArrayList<Result>

    @SerializedName("plus_code")
    lateinit var plusCode: PlusCode
}