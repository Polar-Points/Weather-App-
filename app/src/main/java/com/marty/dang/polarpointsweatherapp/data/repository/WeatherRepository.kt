package com.marty.dang.polarpointsweatherapp.data.repository

import com.marty.dang.polarpointsweatherapp.data.OpenWeatherApiService
import com.marty.dang.polarpointsweatherapp.data.room.WeatherCacheDao
import com.marty.dang.polarpointsweatherapp.utils.Transformers
import com.marty.dang.polarpointsweatherapp.presentation.model.DataSourceModel
import com.marty.dang.polarpointsweatherapp.utils.Keys
import timber.log.Timber
import javax.inject.Inject

/**
 *   Created by Marty Dang on 8/21/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class WeatherRepository @Inject constructor(
    private  val webservice: OpenWeatherApiService,
    private val cache: WeatherCacheDao) {

    suspend fun getCurrentWeather(latitude: Double, longitude: Double): DataSourceModel {

        val cacheObject = cache.getWeatherObject()

        // valid cache
        if(cacheObject != null) {
            if(60000 >= (System.currentTimeMillis() - cacheObject.lastTimeAccessed)) {
                return Transformers.transformCacheToDataSourceModel(cacheObject)
            }
        }

        // make a new network request since cache is old
        val data = webservice.getCurrentWeather(latitude, longitude,"minutely,daily", Keys.API_KEY,"imperial")
        val dataSourceModel = Transformers.transformApiToDataSourceModel(data)
        cache.updateCache(Transformers.transformDataSourceModelToCache(dataSourceModel))
        return dataSourceModel
    }
}