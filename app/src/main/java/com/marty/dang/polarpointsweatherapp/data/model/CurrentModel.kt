package com.marty.dang.polarpointsweatherapp.data.model

import com.squareup.moshi.Json

/**
 *   Created by Marty Dang on 8/21/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
data class CurrentModel(
    @Json(name = "dt") var dt: Int? = null,
    @Json(name = "sunrise") var sunrise: Int? = null,
    @Json(name = "sunset") var sunset: Int? = null,
    @Json(name = "temp") var temp: Double? = null,
    @Json(name = "feels_like") var feelsLike: Double? = null,
    @Json(name = "pressure") var pressure: Int? = null,
    @Json(name = "humidity") var humidity: Int? = null,
    @Json(name = "dew_point") var dewPoint: Double? = null,
    @Json(name = "uvi") var uvi: Double? = null,
    @Json(name = "clouds") var clouds: Int? = null,
    @Json(name = "visibility") var visibility: Int? = null,
    @Json(name = "wind_speed") var windSpeed: Double? = null,
    @Json(name = "wind_deg") var windDeg: Int? = null,
    @Json(name = "wind_gust") var windGust: Double? = null,
    @Json(name = "weather") var weather: List<WeatherModel>? = null
)