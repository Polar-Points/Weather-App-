package com.marty.dang.polarpointsweatherapp.presentation.model

/**
 *   Created by Marty Dang on 10/2/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
data class DataSourceModel(
    var currentTempList: List<String>,
    var weatherDescriptionList: List<String>,
    var humidityList: List<String>,
    var precipitationList: List<String>,
    var windList: List<String>,
    var dateList: List<String>,
    var lastTimeAccessed: Long
)