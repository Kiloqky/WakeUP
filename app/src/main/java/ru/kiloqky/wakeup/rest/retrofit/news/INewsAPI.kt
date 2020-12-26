package ru.kiloqky.wakeup.rest.retrofit.news

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.NewsBody

interface INewsAPI {
    @GET("v2/top-headlines")
    fun loadNews(
        @Query("country") country: String,
        @Query("apiKey") api_key: String
    ): Call<NewsBody>
}