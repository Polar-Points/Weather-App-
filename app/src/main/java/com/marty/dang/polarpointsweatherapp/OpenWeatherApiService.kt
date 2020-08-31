package com.marty.dang.polarpointsweatherapp

import com.marty.dang.polarpointsweatherapp.model.CurrentWeatherModel
import retrofit2.Call
import retrofit2.http.GET

/**
 *   Created by Marty Dang on 8/9/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */

// Create a seperate file and insert your key there

interface OpenWeatherApiService {
    @GET("data/2.5/onecall?lat=43&lon=-70&exclude=minutely,hourly,daily&appid=" + Keys.API_KEY + "&units=imperial")
    suspend fun getCurrentWeather(): CurrentWeatherModel
}