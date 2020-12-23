package ru.kiloqky.wakeup.viewmodels

import android.app.Application
import android.provider.Settings.Global.getString
import androidx.core.content.contentValuesOf
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import ru.kiloqky.wakeup.App
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi.GeocodingRepo
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geolocationApi.GeolocationRepo
import ru.kiloqky.wakeup.rest.retrofit.news.NewsAPIRepo
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.Articles
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.NewsBody
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.OpenWeatherRepoOneCall
import java.util.*
import kotlin.collections.ArrayList

class NewsViewModel(
    val newsAPIRepo: NewsAPIRepo,
    application: Application
) : AndroidViewModel(application) {

    private val API_KEY_NEWS: String = application.getString(R.string.API_KEY_NEWS)
    private val _recyclerNews = MutableLiveData<LoadStateWrapper<Articles>>().apply {}
    val recyclerNews: LiveData<LoadStateWrapper<Articles>> = _recyclerNews

    fun fetchNews() {
        viewModelScope.launch {
            _recyclerNews.postValue(LoadStateWrapper(state = LoadState.LOADING))
            newsAPIRepo.api.loadNews(
                Locale.getDefault().country.toString().toLowerCase(Locale.getDefault()),
                API_KEY_NEWS
            ).enqueue(object : retrofit2.Callback<NewsBody> {
                override fun onResponse(call: Call<NewsBody>, response: Response<NewsBody>) {
                    val arrayList: MutableList<Articles> = ArrayList()
                    arrayList.addAll(response.body()!!.articles)
                    _recyclerNews.postValue(
                        LoadStateWrapper(
                            data = arrayList,
                            state = LoadState.SUCCESS
                        )
                    )
                }

                override fun onFailure(call: Call<NewsBody>, t: Throwable) {
                    _recyclerNews.postValue(
                        LoadStateWrapper(
                            state = LoadState.ERROR,
                            error = "Unknown error"
                        )
                    )
                    t.printStackTrace()
                }

            })
        }
    }

    enum class LoadState {
        LOADING,
        SUCCESS,
        ERROR
    }

    data class LoadStateWrapper<T>(
        val data: List<T>? = null,
        val state: LoadState,
        val error: String = ""
    )
}