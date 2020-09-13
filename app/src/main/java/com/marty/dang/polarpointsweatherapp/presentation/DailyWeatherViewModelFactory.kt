package com.marty.dang.polarpointsweatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marty.dang.polarpointsweatherapp.data.repository.CurrentWeatherCache
import com.marty.dang.polarpointsweatherapp.data.repository.LocationDataRepository
import com.marty.dang.polarpointsweatherapp.data.repository.WeatherRepository
import com.marty.dang.polarpointsweatherapp.presentation.viewmodel.DailyWeatherViewModel
import javax.inject.Inject

/**
 *   Created by Marty Dang on 9/8/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class DailyWeatherViewModelFactory @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepo: LocationDataRepository,
    private val currentWeatherCache: CurrentWeatherCache) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DailyWeatherViewModel::class.java)){
            return DailyWeatherViewModel(weatherRepository, locationRepo, currentWeatherCache) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}