package com.marty.dang.polarpointsweatherapp.utils

import android.Manifest

/**
 *   Created by Marty Dang on 9/3/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
object Constants {

    // permissions
    val locationPermissionsArray = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    // request codes
    const val locationCode = 100

    // cache
    const val cacheTempKey = "cacheCurrentTemp"
    const val cacheWeatherIconKey = "cacheWeatherIcon"
    const val cacheWeatherDescriptionKey = "cacheWeatherDescriptionKey"

    // shared prefs
    const val sharedPrefsFile = "sharedPrefs"
    const val sharedPrefsCurrentTempKey = "sharedPrefsCurrentTemp"
    const val sharedPrefsCurrentIconKey = "sharedPrefsCurrentIcon"
    const val sharedPrefsCurrentWeatherDescriptionKey = "sharedPrefsWeatherDescription"
    const val sharedPrefsLastRequestTimeKey = "sharedPrefsRequestTime"
}