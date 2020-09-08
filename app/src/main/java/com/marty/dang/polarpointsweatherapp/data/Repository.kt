package com.marty.dang.polarpointsweatherapp.data

import com.marty.dang.polarpointsweatherapp.data.model.CurrentWeatherModel

/**
 *   Created by Marty Dang on 9/6/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
interface Repository {

    suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeatherModel
}