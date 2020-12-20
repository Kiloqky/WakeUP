package ru.kiloqky.wakeup.viewmodels


import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi.GeocodingRepo
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geocodingApi.entities.GeocodingMain
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geolocationApi.GeolocationRepo
import ru.kiloqky.wakeup.rest.retrofit.geolocation.geolocationApi.entities.Geolocation
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.OpenWeatherRepoOneCall
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities.Daily
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities.Hourly
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities.WeatherMain
import java.util.*

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val apiKeyWeather = application.getString(R.string.API_KEY_WEATHER)
    private val apiKeyGeolocation = application.getString(R.string.API_KEY_GEOLOCATION)

    private val _cityName = MutableLiveData<LoadStateWrapper<String>>().apply {}
    private val _icon = MutableLiveData<String>().apply {}
    private val _temp = MutableLiveData<String>().apply {}
    private val _weather = MutableLiveData<String>().apply {}
    private val _feel = MutableLiveData<String>().apply {}

    private val _recyclerViewToday = MutableLiveData<Array<Hourly>>().apply {}
    private val _recyclerViewMoreDays = MutableLiveData<Array<Daily>>().apply {}

    val cityName: LiveData<LoadStateWrapper<String>> = _cityName
    val icon: LiveData<String> = _icon
    val temp: LiveData<String> = _temp
    val weather: LiveData<String> = _weather
    val feel: LiveData<String> = _feel

    val app = application

    val recyclerViewToday: LiveData<Array<Hourly>> = _recyclerViewToday
    val recyclerViewMoreDays: LiveData<Array<Daily>> = _recyclerViewMoreDays

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
                            initCityName(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<Geolocation?>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }

    }

    private fun initCityName(body: Geolocation) {
        GeocodingRepo.Singleton.api.loadLocation(
            body.location!!.lat.toString() + "," + body.location!!.lng.toString(), apiKeyGeolocation
        ).enqueue(object : retrofit2.Callback<GeocodingMain> {
            override fun onResponse(call: Call<GeocodingMain>, response: Response<GeocodingMain>) {
                initCity(body, response.body()!!.results[4].address)
            }

            override fun onFailure(call: Call<GeocodingMain>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun initCity(body: Geolocation, address: String) {
        OpenWeatherRepoOneCall.Singleton.api.loadWeather(
            lat = body.location!!.lat,
            lon = body.location!!.lng,
            lang = Locale.getDefault().toString().toLowerCase(Locale.getDefault()).split("_")[0],
            keyAPI = apiKeyWeather
        )
            .enqueue(object : retrofit2.Callback<WeatherMain> {
                override fun onResponse(call: Call<WeatherMain>, response: Response<WeatherMain>) {
                    println(address)
                    println(response.body()!!.current.dt)
                    initCityInfo(response.body(), address)
                }

                override fun onFailure(call: Call<WeatherMain>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun initCityInfo(body: WeatherMain?, address: String) {
        //название города
        _cityName.postValue(LoadStateWrapper(address, LoadState.SUCCESS))
        //иконка погоды
        _icon.postValue(getIcon(body!!.current.weather[0].icon))
        //температура
        _temp.postValue(
            String.format(
                Locale.getDefault(), "%.0f",
                body.current.temp - 272
            ) + "\u2103"
        )
        //ощущается как
        _feel.postValue(
            app.getString(R.string.feel_like) + String.format(
                Locale.getDefault(), "%.0f",
                body.current.feelsLike - 272
            ) + "\u2103"
        )
        //название погоды
        _weather.postValue(body.current.weather[0].description)
        //список погоды на сегодня
        _recyclerViewToday.postValue(
            body.hourly
        )
        //список погоды на другие дни
        _recyclerViewMoreDays.postValue(
            body.daily
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
