package com.marty.dang.polarpointsweatherapp.data.repository

import android.content.Context
import com.marty.dang.polarpointsweatherapp.utils.Constants

/**
 *   Created by Marty Dang on 9/4/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class CurrentWeatherCache(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(Constants.sharedPrefsFile, Context.MODE_PRIVATE)
    private val editor = sharedPrefs.edit()

    val refreshInMilliseconds = 60000

    // saved time of network request
    var lastTimeAccessed: Long
        get() {
            return sharedPrefs.getLong(Constants.sharedPrefsLastRequestTimeKey,-1)
        }
        set(value) {
            editor.putLong(Constants.sharedPrefsLastRequestTimeKey, value).apply()
        }

    // if we have made a network request before, should have save value
    val isCurrentWeatherCacheEmpty: Boolean
        get() {
            return lastTimeAccessed == -1L
        }

    fun getCurrentWeatherFromCache(): Map<String,String> {
        val cacheMap = mutableMapOf<String,String>()
        cacheMap[Constants.cacheTempKey] = sharedPrefs.getString(Constants.sharedPrefsCurrentTempKey,"") ?: ""
        cacheMap[Constants.cacheWeatherIconKey] = sharedPrefs.getString(Constants.sharedPrefsCurrentIconKey,"") ?: ""
        cacheMap[Constants.cacheWeatherDescriptionKey] = sharedPrefs.getString(Constants.sharedPrefsCurrentWeatherDescriptionKey,"") ?: ""
        return cacheMap
    }

    fun saveCurrentWeatherToCache(cacheMap: Map<String,String>){
        editor.putString(Constants.sharedPrefsCurrentTempKey, cacheMap[Constants.cacheTempKey]).apply()
        editor.putString(Constants.sharedPrefsCurrentIconKey, cacheMap[Constants.cacheWeatherIconKey]).apply()
        editor.putString(Constants.sharedPrefsCurrentWeatherDescriptionKey, cacheMap[Constants.cacheWeatherDescriptionKey]).apply()
    }
}