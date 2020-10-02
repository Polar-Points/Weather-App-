package com.marty.dang.polarpointsweatherapp.data.model

import com.squareup.moshi.Json

/**
 *   Created by Marty Dang on 8/9/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */

data class CurrentWeatherModel(
    @field:Json(name = "lat") val lat: Double? = null,
    @field:Json(name = "lon") val lon: Double? = null,
    @field:Json(name = "timezone") val timezone: String? = null,
    @field:Json(name = "timezone_offset") val timezoneOffset: Int? = null,
    @field:Json(name = "current") val current: CurrentModel? = null,
    @field:Json(name = "hourly") var hourly: List<HourlyModel>? = null
)