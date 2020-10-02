package com.marty.dang.polarpointsweatherapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *   Created by Marty Dang on 9/16/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
@Entity(tableName = "weather_cache")
data class WeatherObject(
    @PrimaryKey @ColumnInfo(name = "weather")
    var currentTempList: String,
    var weatherDescriptionList: String,
    var humidityList: String,
    var precipitationList: String,
    var windList: String,
    var dateList: String,
    var lastTimeAccessed: Long
)