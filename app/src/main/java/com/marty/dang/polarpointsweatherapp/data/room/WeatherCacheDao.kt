package com.marty.dang.polarpointsweatherapp.data.room

import androidx.room.*

/**
 *   Created by Marty Dang on 9/16/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
@Dao
interface WeatherCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateCache(weatherObject: WeatherObject)

    @Query("DELETE FROM weather_cache")
    fun deleteCache()

    @Query("SELECT * FROM weather_cache")
    fun getWeatherObject(): WeatherObject?
}