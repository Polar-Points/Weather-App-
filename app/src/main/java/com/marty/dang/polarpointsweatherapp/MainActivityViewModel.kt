package com.marty.dang.polarpointsweatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

/**
 *   Created by Marty Dang on 8/21/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class MainActivityViewModel(): ViewModel() {

    val weatherRepo: WeatherRepository = WeatherRepository()

    val weather = liveData(Dispatchers.IO) {
        val retrivedTodo = weatherRepo.getCurrentWeather()
        emit(retrivedTodo)
    }
}