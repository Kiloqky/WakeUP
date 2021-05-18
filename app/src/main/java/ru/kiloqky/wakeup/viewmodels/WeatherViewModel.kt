package ru.kiloqky.wakeup.viewmodels


import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

/**
 * я бы и переписал это, но у меня закончилась подписка на Geocoding API
 * продлевать ее не сильно хочется, просто поверьте что это работает, хоть и не так как я хотел бы сейчас
 * 18.05.2021 (уровень 26.12.2020) **/

class WeatherViewModel(
    private val geolocationRepo: GeolocationRepo,
    private val geocodingRepo: GeocodingRepo,
    private val openWeatherRepoOneCall: OpenWeatherRepoOneCall,
    private val application: Application
) : ViewModel() {


    private val apiKeyWeather = application.getString(R.string.API_KEY_WEATHER)
    private val apiKeyGeolocation = application.getString(R.string.API_KEY_GEOLOCATION)

    private val _cityName = MutableLiveData<LoadStateWrapper<String>>()
    private val _icon = MutableLiveData<String>()
    private val _temp = MutableLiveData<String>()
    private val _weather = MutableLiveData<String>()
    private val _feel = MutableLiveData<String>()

    private val _recyclerViewToday = MutableLiveData<List<Hourly>>()
    private val _recyclerViewMoreDays = MutableLiveData<List<Daily>>()

    val cityName: LiveData<LoadStateWrapper<String>> = _cityName
    val icon: LiveData<String> = _icon
    val temp: LiveData<String> = _temp
    val weather: LiveData<String> = _weather
    val feel: LiveData<String> = _feel

    val recyclerViewToday: LiveData<List<Hourly>> = _recyclerViewToday
    val recyclerViewMoreDays: LiveData<List<Daily>> = _recyclerViewMoreDays

    fun refreshCity() {
        _cityName.value = LoadStateWrapper(state = LoadState.LOADING)
        with(CoroutineScope(SupervisorJob() + Dispatchers.IO)) {
            launch {
                geolocationRepo.api.loadLocation(apiKeyGeolocation)
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
                            _cityName.postValue(LoadStateWrapper(state = LoadState.ERROR))
                            t.printStackTrace()
                        }
                    })
            }
        }
    }


    private fun initCityName(body: Geolocation) {
        geocodingRepo.api.loadLocation(
            body.location!!.lat.toString() + ","
                    + body.location!!.lng.toString(),
            apiKeyGeolocation
        )
            .enqueue(object : retrofit2.Callback<GeocodingMain> {
                override fun onResponse(
                    call: Call<GeocodingMain>,
                    response: Response<GeocodingMain>
                ) {
                    var address = response.body()!!.plusCode.compound.split(" ")[1]
                    address = address.substring(0, address.length - 1)
                    initCity(body, address)
                }

                override fun onFailure(call: Call<GeocodingMain>, t: Throwable) {
                    _cityName.postValue(LoadStateWrapper(state = LoadState.ERROR))
                    t.printStackTrace()
                }
            })
    }

    private fun initCity(body: Geolocation, address: String) {
        openWeatherRepoOneCall.api.loadWeather(
            lat = body.location!!.lat,
            lon = body.location!!.lng,
            lang = Locale.getDefault().toString().toLowerCase(Locale.getDefault()).split("_")[0],
            keyAPI = apiKeyWeather
        )
            .enqueue(object : retrofit2.Callback<WeatherMain> {
                override fun onResponse(call: Call<WeatherMain>, response: Response<WeatherMain>) {
                    initWeatherInfo(response.body(), address)
                }

                override fun onFailure(call: Call<WeatherMain>, t: Throwable) {
                    _cityName.postValue(LoadStateWrapper(state = LoadState.ERROR))
                    t.printStackTrace()
                }
            })
    }

    private fun initWeatherInfo(body: WeatherMain?, address: String) {
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
            application.getString(R.string.feel_like) + String.format(
                Locale.getDefault(), "%.0f",
                body.current.feelsLike - 272
            ) + "\u2103"
        )
        //название погоды
        _weather.postValue(body.current.weather[0].description)
        //список погоды на сегодня
        val hourly: List<Hourly> = body.hourly.subList(1, body.hourly.size)
        _recyclerViewToday.postValue(hourly)
        //список погоды на другие дни
        val daily: List<Daily> = body.daily.subList(1, body.daily.size)
        _recyclerViewMoreDays.postValue(daily)
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

    class LoadStateWrapper<T>(
        val data: T? = null,
        val state: LoadState,
        val error: String = "unknown error"
    )

}
