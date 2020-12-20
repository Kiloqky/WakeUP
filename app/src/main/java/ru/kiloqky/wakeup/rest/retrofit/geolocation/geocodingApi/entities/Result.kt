package ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi.entities

import com.google.gson.annotations.SerializedName

class Result {
    @SerializedName("formatted_address")
    lateinit var address: String
    //5
}