package ru.kiloqky.wakeup.viewmodels


import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.retrofit.geolocation.GeolocationRepo
import ru.kiloqky.wakeup.rest.retrofit.geolocation.entitiesGeolocation.Geolocation
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast.OpenWeatherRepoForeCast
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast.entitiesOpenWeather.WeatherList
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast.entitiesOpenWeather.WeatherLists
import java.util.*
import kotlin.collections.ArrayList

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val apiKeyWeather = application.getString(R.string.API_KEY_WEATHER)
    private val apiKeyGeolocation = application.getString(R.string.API_KEY_GEOLOCATION)

    private val _cityName = MutableLiveData<LoadStateWrapper<String>>().apply {}
    private val _icon = MutableLiveData<String>().apply {}
    private val _temp = MutableLiveData<String>().apply {}
    private val _weather = MutableLiveData<String>().apply {}
    private val _feel = MutableLiveData<String>().apply {}

    private val _recyclerViewToday = MutableLiveData<ArrayList<WeatherList>>().apply {}
    private val _recyclerViewMoreDays = MutableLiveData<ArrayList<WeatherList>>().apply {}

    val cityName: LiveData<LoadStateWrapper<String>> = _cityName
    val icon: LiveData<String> = _icon
    val temp: LiveData<String> = _temp
    val weather: LiveData<String> = _weather
    val feel: LiveData<String> = _feel

    val app = application

    val recyclerViewToday: LiveData<ArrayList<WeatherList>> = _recyclerViewToday
    val recyclerViewMoreDays: LiveData<ArrayList<WeatherList>> = _recyclerViewMoreDays

    fun refreshCity() {
        _cityName.value = LoadStateWrapper(state = LoadState.LOADING)
        viewModelScope.launch {
            GeolocationRepo.Singleton.api.loadLocation(apiKeyGeolocation)
                .enqueue(object : retrofit2.Callback<Geolocation?> {
                    override fun onResponse(
                        call: Call<Geolocation?>,
                        response: Response<Geolocation?>
                    ) {
                        if (response.body() != null && response.isSuccessful) {
                            initCity(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<Geolocation?>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }

    }

    private fun initCity(body: Geolocation) {
        OpenWeatherRepoForeCast.Singleton.api.loadWeather(
            body.location!!.lat,
            body.location!!.lng,
            apiKeyWeather
        )!!
            .enqueue(object : retrofit2.Callback<WeatherLists?> {
                override fun onResponse(
                    call: Call<WeatherLists?>,
                    response: Response<WeatherLists?>
                ) {
                    initCityInfo(response.body())
                }

                override fun onFailure(call: Call<WeatherLists?>, t: Throwable) {
                    _cityName.postValue(
                        LoadStateWrapper(
                            state = LoadState.ERROR,
                            error = "Unknown error"
                        )
                    )
                }
            })
    }

    private fun initCityInfo(body: WeatherLists?) {
        //название города
        _cityName.postValue(LoadStateWrapper(body!!.cityRestModel.name, LoadState.SUCCESS))
        //иконка погоды
        _icon.postValue(getIcon(body.weatherLists[0].weatherRestModel[0].icon))
        //температура
        _temp.postValue(
            String.format(
                Locale.getDefault(), "%.0f",
                body.weatherLists[0].mainRestModel.temp - 272
            ) + "\u2103"
        )
        //ощущается как
        _feel.postValue(
            app.getString(R.string.feel_like) + String.format(
                Locale.getDefault(), "%.0f",
                body.weatherLists[0].mainRestModel.feelsLike - 272
            ) + "\u2103"
        )
        //название погоды
        _weather.postValue(body.weatherLists[0].weatherRestModel[0].main)
        //список погоды на сегодня
        _recyclerViewToday.postValue(
            java.util.ArrayList(listOf(*body.weatherLists).subList(0, 16))
        )
        //список погоды на другие дни
        val arrayList = ArrayList<WeatherList>()
        arrayList.addAll(body.weatherLists)
        _recyclerViewMoreDays.postValue(
            arrayList
        )
    }

    private fun getIcon(icon: String): String {
        when (icon) {
            "01d" -> return "\uF06E"
            "01n" -> return "\uF0A3"
            "02d" -> return "\uF002"
            "02n" -> return "\uF031"
            "03d" -> return "\uF041"
            "03n" -> return "\uF041"
            "04d" -> return "\uF013"
            "04n" -> return "\uF013"
            "09d" -> return "\uF015"
            "09n" -> return "\uF015"
            "10d" -> return "\uF009"
            "10n" -> return "\uF027"
            "11d" -> return "\uF005"
            "11n" -> return "\uF02C"
            "13d" -> return "\uF00A"
            "13n" -> return "\uF02A"
            "50d" -> return "\uF082"
            "50n" -> return "\uF082"
        }
        return "\uF075"
    }

    enum class LoadState {
        LOADING,
        SUCCESS,
        ERROR
    }

    data class LoadStateWrapper<T>(
        val data: T? = null,
        val state: LoadState,
        val error: String = "unknown error"
    )
}
