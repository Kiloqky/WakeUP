package ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews

import com.google.gson.annotations.SerializedName
import ru.kiloqky.wakeup.rest.NewsData

class Articles : NewsData() {
    @SerializedName("source")
    var source: Source? = null

    @SerializedName("author")
    var author: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("urlToImage")
    var urlToImage: String? = null

    @SerializedName("publishedAt")
    var publishedAt: String? = null
}