package com.marty.dang.polarpointsweatherapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.marty.dang.polarpointsweatherapp.R
import java.util.*
import javax.inject.Inject

/**
 *   Created by Marty Dang on 9/12/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class LocationDataRepository @Inject constructor(
    private val context: Context,
    private val geocoder: Geocoder,
    private val locationManager: LocationManager
) {

    @SuppressLint("MissingPermission")
    fun getCurrentLocationCoordinates(): Map<String, Double> {
        var latitude: Double
        var longitude: Double
        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).let { location ->
            latitude = location?.latitude!!
            longitude = location.longitude
        }

        val mapping = mutableMapOf<String, Double>()
        mapping["latitude"] = latitude
        mapping["longitude"] = longitude
        return mapping
    }

    fun convertCoordinatesToLocation(latitude: Double, longitude: Double): String {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return context.getString(R.string.home_frag_location_string, addresses[0].locality,addresses[0].adminArea)
    }
}