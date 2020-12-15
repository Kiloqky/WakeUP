package ru.kiloqky.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kiloqky.weather.R
import ru.kiloqky.weather.rest.geolocation.GeolocationRepo
import ru.kiloqky.weather.rest.geolocation.entitiesGeolocation.Geolocation


class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {}
    val text: LiveData<String> = _text

    private val _location = MutableLiveData<LoadStateWrapper<Geolocation>>()
    val location: LiveData<LoadStateWrapper<Geolocation>> = _location

    fun fetchGallery() {
        _location.value = LoadStateWrapper(state = LoadState.LOADING)
        viewModelScope.launch {
            GeolocationRepo.Singleton.api.loadLocation(R.string.LOCATION_API_KEY.toString())
                .enqueue(object : Callback<Geolocation> {
                    override fun onResponse(
                        call: Call<Geolocation?>,
                        response: Response<Geolocation?>
                    ) {
                        _location.postValue(LoadStateWrapper(response.body(), LoadState.SUCCESS))
                    }

                    override fun onFailure(call: Call<Geolocation?>, t: Throwable) {
                        _location.postValue(
                            LoadStateWrapper(
                                state = LoadState.ERROR,
                                error = t.message.toString()
                            )
                        )
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
        val data: T? = null,
        val state: LoadState,
        val error: String = ""
    )


}