package com.marty.dang.polarpointsweatherapp.data.repository

import com.marty.dang.polarpointsweatherapp.BuildConfig
import com.marty.dang.polarpointsweatherapp.data.OpenWeatherApiService
import com.marty.dang.polarpointsweatherapp.data.model.CurrentWeatherModel
import com.marty.dang.polarpointsweatherapp.utils.Keys
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

/**
 *   Created by Marty Dang on 8/21/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class WeatherRepository @Inject constructor(
    private  val webservice: OpenWeatherApiService,
    private val httpClient: OkHttpClient.Builder) {

    suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeatherModel {
        if(BuildConfig.DEBUG){
            val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging).build()
        }
        return webservice.getCurrentWeather(latitude, longitude,"minutely,hourly,daily", Keys.API_KEY,"imperial")
    }
}