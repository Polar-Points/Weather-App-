package com.marty.dang.polarpointsweatherapp.data.model

import com.squareup.moshi.Json

/**
 *   Created by Marty Dang on 8/21/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
data class WeatherModel(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "main") var main: String? = null,
    @Json(name = "description") var description: String? = null,
    @Json(name = "icon") var icon: String? = null
)