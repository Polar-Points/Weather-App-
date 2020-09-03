package com.marty.dang.polarpointsweatherapp.data.model

import com.marty.dang.polarpointsweatherapp.data.model.CurrentModel
import com.squareup.moshi.Json

/**
 *   Created by Marty Dang on 8/9/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */

// @json in Moshi = @SerializedName in Gson


data class CurrentWeatherModel(
    @field:Json(name = "lat") val lat: Int? = null,
    @field:Json(name = "lon") val lon: Int? = null,
    @field:Json(name = "timezone") val timezone: String? = null,
    @field:Json(name = "timezone_offset") val timezoneOffset: Int? = null,
    @field:Json(name = "current") val current: CurrentModel? = null
)