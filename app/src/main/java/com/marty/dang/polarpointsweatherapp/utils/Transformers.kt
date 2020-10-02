package com.marty.dang.polarpointsweatherapp.utils

import com.marty.dang.polarpointsweatherapp.data.model.CurrentWeatherModel
import com.marty.dang.polarpointsweatherapp.data.room.WeatherObject
import com.marty.dang.polarpointsweatherapp.presentation.model.DataSourceModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 *   Created by Marty Dang on 10/2/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */

// transforms data from repository into format that can be consumed by view model
object Transformers {

    // transforms API data -> DataSourceModel
    fun transformApiToDataSourceModel(currentWeatherModel: CurrentWeatherModel) : DataSourceModel {
        val currentTempList = mutableListOf<String>()
        val weatherDescriptionList = mutableListOf<String>()
        val humidityList = mutableListOf<String>()
        val precipitationList = mutableListOf<String>()
        val windList = mutableListOf<String>()
        val dateList = mutableListOf<String>()
        val lastTimeAccessed = Calendar.getInstance().timeInMillis

        // add in the current temp first
        currentTempList.add(currentWeatherModel.current?.temp?.roundToInt().toString()+" \u2109")
        weatherDescriptionList.add(currentWeatherModel.current?.weather?.get(0)?.description.toString())
        humidityList.add(currentWeatherModel.current?.humidity.toString())
        precipitationList.add((currentWeatherModel.hourly?.get(0)?.pop?.times(100)).toString())
        windList.add(currentWeatherModel.current?.windSpeed.toString())

        val date = currentWeatherModel.current?.dt?.times(1000L)
        val sdf = SimpleDateFormat("MM/dd/yyy h:mm a", Locale.US)
        val humanTime = sdf.format(Date(date!!))
        dateList.add(humanTime)

        // be sure to skip the first entry in the hourly
        // transform into DataSourceModel so view model can consume
        for(hourly in currentWeatherModel.hourly.orEmpty().subList(1, 25)){
            currentTempList.add(hourly.temp?.roundToInt().toString()+" \u2109")
            weatherDescriptionList.add(hourly.weather?.get(0)?.description.toString())
            humidityList.add(hourly.humidity.toString())
            precipitationList.add((hourly.pop?.times(100)).toString())
            windList.add(hourly.windSpeed.toString())

            Timber.d("weather desc %s", hourly.weather?.get(0)?.description.toString())

            val date = hourly.dt?.times(1000L)

            val sdf = SimpleDateFormat("MM/dd/yyy h:mm a", Locale.US)
            val humanTime = sdf.format(Date(date!!))
            dateList.add(humanTime)
        }

        return DataSourceModel(
            currentTempList,
            weatherDescriptionList,
            humidityList,
            precipitationList,
            windList,
            dateList,
            lastTimeAccessed
        )
    }

    fun transformDataSourceModelToCache(dataSourceModel: DataSourceModel): WeatherObject {
        var currentTempString = ""
        var weatherDescriptionString = ""
        var humidityString = ""
        var precipitationString = ""
        var windList = ""
        var dateList = ""
        val lastTimeAccessed = dataSourceModel.lastTimeAccessed

        for(data in dataSourceModel.currentTempList) {
            currentTempString += "$data/"
        }

        for(data in dataSourceModel.weatherDescriptionList) {
            weatherDescriptionString += "/$data"
        }

        for(data in dataSourceModel.humidityList) {
            humidityString += "/$data"
        }

        for(data in dataSourceModel.precipitationList) {
            precipitationString += "/$data"
        }

        for(data in dataSourceModel.windList) {
            windList += "/$data"
        }

        for(data in dataSourceModel.dateList) {
            dateList += "/$data"
        }

        return WeatherObject(
            currentTempString,
            weatherDescriptionString,
            humidityString,
            precipitationString,
            windList,
            dateList,
            lastTimeAccessed
        )
    }

    fun transformCacheToDataSourceModel(weatherObject: WeatherObject) : DataSourceModel  {
        val currentTempList = weatherObject.currentTempList.split("/")
        val weatherDescriptionList = weatherObject.weatherDescriptionList.split("/")
        val humidityList = weatherObject.humidityList.split("/")
        val precipitationList = weatherObject.precipitationList.split("/")
        val windList = weatherObject.windList.split("/")
        val dateList = weatherObject.dateList.split("/")
        val lastTimeAccessed = weatherObject.lastTimeAccessed

        return DataSourceModel(
            currentTempList,
            weatherDescriptionList,
            humidityList,
            precipitationList,
            windList,
            dateList,
            lastTimeAccessed
        )
    }
}