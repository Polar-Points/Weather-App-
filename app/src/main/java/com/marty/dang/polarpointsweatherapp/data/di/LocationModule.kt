package com.marty.dang.polarpointsweatherapp.data.di

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

/**
 *   Created by Marty Dang on 9/13/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
@Module
class LocationModule {

    @Singleton
    @Provides
    fun provideGeocoder(context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }

    @Singleton
    @Provides
    fun provideLocationManager(context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}