package com.marty.dang.polarpointsweatherapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *   Created by Marty Dang on 9/16/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
@Database(entities = [WeatherObject::class], version = 1, exportSchema = false)
abstract class WeatherCacheDatabase: RoomDatabase() {

    abstract fun weatherCacheDao(): WeatherCacheDao
}