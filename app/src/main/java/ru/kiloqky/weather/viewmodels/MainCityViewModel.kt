package ru.kiloqky.weather.viewmodels


import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import ru.kiloqky.weather.R
import ru.kiloqky.weather.rest.geolocation.GeolocationRepo
import ru.kiloqky.weather.rest.geolocation.entitiesGeolocation.Geolocation
import ru.kiloqky.weather.rest.openWeatherMap.OpenWeatherRepo
import ru.kiloqky.weather.rest.openWeatherMap.entitiesOpenWeather.WeatherList
import ru.kiloqky.weather.rest.openWeatherMap.entitiesOpenWeather.WeatherLists
import java.util.*
import kotlin.collections.ArrayList

class MainCityViewModel : ViewModel() {
    private val _cityName = MutableLiveData<LoadStateWrapper<String>>().apply {}
    private val _icon = MutableLiveData<String>().apply {}
    private val _temp = MutableLiveData<String>().apply {}
    private val _weather = MutableLiveData<String>().apply {}
    private val _feel = MutableLiveData<String>().apply {}

    //    private val _wind = MutableLiveData<String>().apply {}
//    private val _pressure = MutableLiveData<String>().apply {}
//    private val _humidity = MutableLiveData<String>().apply {}
    private val _recyclerViewToday = MutableLiveData<ArrayList<WeatherList>>().apply {}
    private val _recyclerViewMoreDays = MutableLiveData<ArrayList<WeatherList>>().apply {}

    val cityName: LiveData<LoadStateWrapper<String>> = _cityName
    val icon: LiveData<String> = _icon
    val temp: LiveData<String> = _temp
    val weather: LiveData<String> = _weather
    val feel: LiveData<String> = _feel

    //    val wind: LiveData<String> = _wind
//    val pressure: LiveData<String> = _pressure
//    val humidity: LiveData<String> = _humidity
    val recyclerViewToday: LiveData<ArrayList<WeatherList>> = _recyclerViewToday
    val recyclerViewMoreDays: LiveData<ArrayList<WeatherList>> = _recyclerViewMoreDays

    fun refreshCity() {
        _cityName.value = LoadStateWrapper(state = LoadState.LOADING)
        viewModelScope.launch {
            GeolocationRepo.Singleton.api.loadLocation(R.string.LOCATION_API_KEY.toString())
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
        OpenWeatherRepo.Singleton.api.loadWeather(
            body.location!!.lat,
            body.location!!.lng,
            R.string.OPENWEATHERAPI_KEY.toString()
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
                            error = "Не удалость поулчить информацию о городе"
                        )
                    )
                }
            })
    }

    private fun initCityInfo(body: WeatherLists?) {
        _cityName.postValue(LoadStateWrapper(body!!.cityRestModel.name, LoadState.SUCCESS))
        _icon.postValue(getIcon(body.weatherLists[0].weatherRestModel[0].icon))
        _temp.postValue(
            String.format(
                Locale.getDefault(), "%.0f",
                body.weatherLists[0].mainRestModel.temp - 272
            ) + "\u2103"
        )
        _feel.postValue(
            "Feel like: " + String.format(
                Locale.getDefault(), "%.0f",
                body.weatherLists[0].mainRestModel.feelsLike - 272
            ) + "\u2103"
        )
        _weather.postValue(body.weatherLists[0].weatherRestModel[0].main)
//        _wind.postValue(
//            "\uF014" + String.format(
//                Locale.getDefault(),
//                "%1.0f",
//                body.weatherLists[0].windRestModel.speed
//            ) + " m/s"
//        )
//        _pressure.postValue(
//            "\uF079" + String.format(
//                Locale.getDefault(),
//                "%1.0f",
//                body.weatherLists[0].mainRestModel.pressure
//            )
//        )
//        _humidity.postValue(
//            "\uF07A" + String.format(
//                Locale.getDefault(),
//                "%1.0f",
//                body.weatherLists[0].mainRestModel.humidity
//            ) + "%"
//        )
        _recyclerViewToday.postValue(
            java.util.ArrayList(listOf(*body.weatherLists).subList(0, 16))
        )
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
        val error: String = ""
    )
}