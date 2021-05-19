package ru.kiloqky.wakeup.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.NewsData
import ru.kiloqky.wakeup.rest.retrofit.news.NewsAPIRepo
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.Articles
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.NewsBody
import ru.kiloqky.wakeup.rest.room.news.NewsDataBase
import ru.kiloqky.wakeup.rest.room.news.model.News
import java.util.*
import kotlin.collections.ArrayList

class NewsViewModel(
    private val newsApiRepo: NewsAPIRepo,
    application: Application,
    private val newsDataBase: NewsDataBase
) : ViewModel() {
    private val apiKeyNews: String = application.getString(R.string.API_KEY_NEWS)

    private val _recyclerNews =
        MutableStateFlow(LoadStateWrapper(state = LoadState.LOADING))
    val recyclerNews: StateFlow<LoadStateWrapper> = _recyclerNews.asStateFlow()

    private val _broadcastArticleLD =
        MutableSharedFlow<NewsData>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val broadcastNewsLD: SharedFlow<NewsData> = _broadcastArticleLD.asSharedFlow()

    fun fetchNews(withoutInfo: Boolean) {
        if (withoutInfo) {
            viewModelScope.launch {
                Log.i("news", "cash")
                val data = newsDataBase.newsDao().getAll()
                _recyclerNews.tryEmit(
                    LoadStateWrapper(
                        state = LoadState.LOADING,
                        data = data
                    )
                )
            }
        }
        with(CoroutineScope(SupervisorJob() + Dispatchers.IO)) {
            launch {
                newsApiRepo.api.loadNews(
                    Locale.getDefault().country.toString().lowercase(Locale.getDefault()),
                    apiKeyNews
                ).enqueue(object : retrofit2.Callback<NewsBody> {
                    override fun onResponse(call: Call<NewsBody>, response: Response<NewsBody>) {
                        val arrayList: MutableList<Articles> = ArrayList()
                        arrayList.addAll(response.body()!!.articles)
                        viewModelScope.launch {
                            _recyclerNews.emit(
                                LoadStateWrapper(
                                    data = arrayList,
                                    state = LoadState.SUCCESS
                                )
                            )
                        }
                        viewModelScope.launch {
                            newsDataBase.newsDao().deleteAll()
                            val cashList: MutableList<News> = mutableListOf()
                            arrayList.forEach { article ->
                                cashList.add(
                                    News(
                                        article.source?.name,
                                        article.title,
                                        article.description,
                                        article.url,
                                        article.urlToImage
                                    )
                                )
                            }
                            newsDataBase.newsDao().addAll(cashList as List<News>)
                        }

                    }

                    override fun onFailure(call: Call<NewsBody>, t: Throwable) {
                        viewModelScope.launch {
                            _recyclerNews.emit(
                                LoadStateWrapper(
                                    state = LoadState.ERROR,
                                )
                            )
                        }

                        t.printStackTrace()
                    }

                })
            }
        }
    }

    fun broadcastNews(article: NewsData) {
        viewModelScope.launch { _broadcastArticleLD.emit(article) }
    }

    enum class LoadState {
        LOADING,
        SUCCESS,
        ERROR
    }

    data class LoadStateWrapper(
        val data: List<NewsData>? = null,
        val state: LoadState,
        val error: String = "unknown error"
    )
}