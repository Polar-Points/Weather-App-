package com.marty.dang.polarpointsweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marty.dang.polarpointsweatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt

class HomeViewModel : ViewModel() {

    private val weatherRepo: WeatherRepository = WeatherRepository()

    val tempObservable: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val iconTypeObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val weatherDescriptionObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val requestMadeTimeObservable: MutableLiveData<String> by lazy {MutableLiveData<String>() }

    fun getWeather(latitude: Double, longitude: Double){
        GlobalScope.launch(Dispatchers.IO) {
            val currentWeatherModel = weatherRepo.getCurrentWeather(latitude, longitude)
            tempObservable.postValue(currentWeatherModel.current?.temp?.roundToInt())
            iconTypeObservable.postValue(currentWeatherModel.current?.weather?.get(0)?.main)
            weatherDescriptionObservable.postValue(currentWeatherModel.current?.weather?.get(0)?.description)
            requestMadeTimeObservable.postValue(Calendar.getInstance().time.toString())
        }
    }
}