package com.marty.dang.polarpointsweatherapp.data.repository

import android.content.Context
import com.marty.dang.polarpointsweatherapp.utils.Constants
import javax.inject.Inject

/**
 *   Created by Marty Dang on 9/4/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */

// Very basic cache to help limit API requests

class CurrentWeatherCache @Inject constructor(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(Constants.sharedPrefsFile, Context.MODE_PRIVATE)
    private val editor = sharedPrefs.edit()

    // amt of time between API calls
    val refreshInMilliseconds = 60000

    // saved time of network request
    var lastTimeAccessed: Long
        get() {
            return sharedPrefs.getLong(Constants.sharedPrefsLastRequestTimeKey,-1)
        }
        set(value) {
            editor.putLong(Constants.sharedPrefsLastRequestTimeKey, value).apply()
        }

    // if we have made a network request before, value should not be -1
    val isCurrentWeatherCacheEmpty: Boolean
        get() {
            return lastTimeAccessed == -1L
        }

    // return a map of the fields we display in the daily frag
    fun getCurrentWeatherFromCache(): Map<String,String> {
        val cacheMap = mutableMapOf<String,String>()
        cacheMap[Constants.cacheTempKey] = sharedPrefs.getString(Constants.sharedPrefsCurrentTempKey,"") ?: ""
        cacheMap[Constants.cacheWeatherIconKey] = sharedPrefs.getString(Constants.sharedPrefsCurrentIconKey,"") ?: ""
        cacheMap[Constants.cacheWeatherDescriptionKey] = sharedPrefs.getString(Constants.sharedPrefsCurrentWeatherDescriptionKey,"") ?: ""
        return cacheMap
    }

    // saving a map of the fields we display in the daily frag
    fun saveCurrentWeatherToCache(cacheMap: Map<String,String>){
        editor.putString(Constants.sharedPrefsCurrentTempKey, cacheMap[Constants.cacheTempKey]).apply()
        editor.putString(Constants.sharedPrefsCurrentIconKey, cacheMap[Constants.cacheWeatherIconKey]).apply()
        editor.putString(Constants.sharedPrefsCurrentWeatherDescriptionKey, cacheMap[Constants.cacheWeatherDescriptionKey]).apply()
    }
}