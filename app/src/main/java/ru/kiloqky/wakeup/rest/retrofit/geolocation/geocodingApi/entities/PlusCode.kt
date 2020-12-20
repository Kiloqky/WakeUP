package ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi.entities

import com.google.gson.annotations.SerializedName

class PlusCode {
    @SerializedName("compound_code")
    lateinit var compound: String
}
