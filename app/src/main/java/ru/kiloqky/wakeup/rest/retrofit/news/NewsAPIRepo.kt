package ru.kiloqky.wakeup.rest.retrofit.news

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIRepo {
    val api = createAdapter()
    private fun createAdapter(): INewsAPI {
        val adapter = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return adapter.create(INewsAPI::class.java)
    }
}