package ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews

import com.google.gson.annotations.SerializedName

class NewsBody {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("articles")
    lateinit var articles: Array<Articles>
}