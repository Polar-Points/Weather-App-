package com.marty.dang.polarpointsweatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.marty.dang.polarpointsweatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {

    private val weatherRepo: WeatherRepository = WeatherRepository()

    val weather = liveData(Dispatchers.IO) {
        val currentWeather = weatherRepo.getCurrentWeather()
        emit(currentWeather)
    }

    fun getWeather(latitude: Double, longitude: Double){
//        val currentWeatherModel = weatherRepo.getCurrentWeather()
//        val temp = currentWeatherModel.current?.temp
//        val iconType = currentWeatherModel.current?.weather?.get(1)
//        val weatherDescription = currentWeatherModel.current?.weather?.get(2)
    }
}