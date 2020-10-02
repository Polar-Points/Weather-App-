package com.marty.dang.polarpointsweatherapp.data.di

import android.content.Context
import androidx.room.Room
import com.marty.dang.polarpointsweatherapp.data.room.WeatherCacheDao
import com.marty.dang.polarpointsweatherapp.data.room.WeatherCacheDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *   Created by Marty Dang on 9/17/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): WeatherCacheDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherCacheDatabase::class.java,
            "weather_cache_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: WeatherCacheDatabase): WeatherCacheDao {
        return database.weatherCacheDao()
    }

}