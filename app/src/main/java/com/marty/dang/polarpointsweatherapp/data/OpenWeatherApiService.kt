package com.marty.dang.polarpointsweatherapp.data

import com.marty.dang.polarpointsweatherapp.utils.Keys
import com.marty.dang.polarpointsweatherapp.data.model.CurrentWeatherModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *   Created by Marty Dang on 8/9/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */

// Create a seperate file and insert your key there

interface OpenWeatherApiService {
    @GET("/data/2.5/onecall")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String,
        @Query("units") unit: String): CurrentWeatherModel
}