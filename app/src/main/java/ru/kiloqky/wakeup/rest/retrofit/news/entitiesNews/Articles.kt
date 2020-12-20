package ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews

import com.google.gson.annotations.SerializedName

class Articles {
    @SerializedName("source")
    lateinit var source: Source

    @SerializedName("author")
    var author: String? = null

    @SerializedName("title")
    lateinit var title: String

    @SerializedName("description")
    lateinit var description: String

    @SerializedName("url")
    lateinit var url: String

    @SerializedName("urlToImage")
    var urlToImage: String? = null

    @SerializedName("publishedAt")
    lateinit var publishedAt: String
}